package vn.com.msb.homeloan.core.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.entity.CommonIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.mapper.CommonIncomeMapper;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.CommonIncomeRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

import java.util.*;

@RequiredArgsConstructor
@Service
public class CommonIncomeServiceImpl implements CommonIncomeService {

  private final CommonIncomeRepository commonIncomeRepository;

  private final LoanApplicationService loanApplicationService;

  private final SalaryIncomeRepository salaryIncomeRepository;

  private final BusinessIncomeRepository businessIncomeRepository;

  private final OtherIncomeRepository otherIncomeRepository;

  @Override
  public CommonIncome save(CommonIncome commonIncome) {

    validateInput(commonIncome);

    loanApplicationService.findById(commonIncome.getLoanApplicationId());

    CommonIncomeEntity entity = commonIncomeRepository.findByLoanApplicationId(
        commonIncome.getLoanApplicationId());
    if (entity == null) {
      // insert
      commonIncome.setUuid(null);
    } else {
      // update
      commonIncome.setUuid(entity.getUuid());
    }

    return CommonIncomeMapper.INSTANCE.toModel(
        commonIncomeRepository.save(CommonIncomeMapper.INSTANCE.toEntity(commonIncome)));
  }

  @Override
  public CommonIncome findById(String uuid) {
    CommonIncomeEntity commonIncomeEntity = commonIncomeRepository.findById(uuid)
        .orElseThrow(
            () -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "common_income:uuid",
                uuid));

    return CommonIncomeMapper.INSTANCE.toModel(commonIncomeEntity);
  }


  /*
   * CHANGE WILL AFFECT MANY PLACES
   * */
  @Override
  public CommonIncome findByLoanApplicationId(String loanId) {
    CommonIncomeEntity commonIncomeEntity = commonIncomeRepository.findByLoanApplicationId(loanId);
    CommonIncome commonIncome = CommonIncomeMapper.INSTANCE.toModel(commonIncomeEntity);
    return commonIncome;
  }

  @Override
  public String[] getSelectedIncomeSubmittedByCustomer(String loanId) {
    List<String> lstSelectedIncome = new ArrayList<>();
    int countSalaryIncome = salaryIncomeRepository.countByLoanApplicationId(loanId);
    if (countSalaryIncome > 0) {
      lstSelectedIncome.add(CMSTabEnum.SALARY_INCOME.getCode());
    }
    int countPersonalBusinessIncome = businessIncomeRepository.countByLoanApplicationIdAndBusinessTypeIn(
        loanId,
        Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION, BusinessTypeEnum.WITHOUT_REGISTRATION));
    if (countPersonalBusinessIncome > 0) {
      lstSelectedIncome.add(CMSTabEnum.PERSONAL_BUSINESS_INCOME.getCode());
    }
    int countBusinessIncome = businessIncomeRepository.countByLoanApplicationIdAndBusinessTypeIn(
        loanId, Arrays.asList(BusinessTypeEnum.ENTERPRISE));
    if (countBusinessIncome > 0) {
      lstSelectedIncome.add(CMSTabEnum.BUSINESS_INCOME.getCode());
    }
    int countOtherIncome = otherIncomeRepository.countByLoanApplicationId(loanId);
    if (countOtherIncome > 0) {
      lstSelectedIncome.add(CMSTabEnum.OTHERS_INCOME.getCode());
    }
    return lstSelectedIncome.toArray(new String[0]);
  }

  private void validateInput(CommonIncome commonIncome) {
    Map<String, String> errorDetail = new HashMap<>();

    if (RecognitionMethod1Enum.EXCHANGE.equals(commonIncome.getRecognitionMethod1())
        && commonIncome.getRecognitionMethod2() == null) {
      errorDetail.put("not_blank.recognition_method_2", "must not be blank");
    }

    // selected income
    if (commonIncome.getSelectedIncomes() != null) {
      if (Arrays.stream(commonIncome.getSelectedIncomes()).distinct().count()
          != commonIncome.getSelectedIncomes().length) {
        errorDetail.put("invalid.selected_income", "duplicate value exists");
      }

      for (String string : commonIncome.getSelectedIncomes()) {
        if (!CMSTabEnum.toIncomeMap.containsKey(string)) {
          errorDetail.put("invalid.selected_income",
              String.format("value must be in [%s]", CMSTabEnum.toIncomeString));
          break;
        }
      }

      // Phương pháp ghi nhận thu nhập 1 là giá trị: Thực nhận hoặc kê khai
      if (RecognitionMethod1Enum.ACTUALLY_RECEIVED.equals(commonIncome.getRecognitionMethod1())
          || RecognitionMethod1Enum.DECLARATION.equals(commonIncome.getRecognitionMethod1())) {
        for (String string : commonIncome.getSelectedIncomes()) {
          if (!CMSTabEnum.toIncomeMap1.containsKey(string)) {
            errorDetail.put("invalid.selected_income",
                String.format("value must be in [%s]", CMSTabEnum.toIncomeString1));
            break;
          }
        }
      }
    }

    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
