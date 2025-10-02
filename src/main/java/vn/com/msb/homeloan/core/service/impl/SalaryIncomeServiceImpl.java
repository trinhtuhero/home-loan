package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.SalaryIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.SalaryIncome;
import vn.com.msb.homeloan.core.model.mapper.SalaryIncomeMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.SalaryIncomeRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.SalaryIncomeService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class SalaryIncomeServiceImpl implements SalaryIncomeService {

  private final SalaryIncomeRepository salaryIncomeRepository;

  private final LoanApplicationRepository loanApplicationRepository;

  private final LoanApplicationService loanApplicationService;

  private final CMSTabActionService cmsTabActionService;

  private final LoanPayerRepository loanPayerRepository;

  private final CommonIncomeService commonIncomeService;

  @Override
  @Transactional
  public List<SalaryIncome> saves(List<SalaryIncome> salaryIncomes) {
    if (CollectionUtils.isEmpty(salaryIncomes)) {
      throw new ApplicationException(ErrorEnum.OTHER_INCOME_NOT_EMPTY);
    } else if (salaryIncomes.size() > 4) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "salary income", String.valueOf(2));
    }

    List<SalaryIncome> list = new ArrayList<>();
    for (SalaryIncome salaryIncome : salaryIncomes) {
      list.add(save(salaryIncome, ClientTypeEnum.CMS));
    }

    if (!CollectionUtils.isEmpty(salaryIncomes)) {
      String loanId = salaryIncomes.get(0).getLoanApplicationId();

      CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

      cmsTabActionService.save(loanId, CMSTabEnum.SALARY_INCOME);
      // delete unselected incomes
      loanApplicationService.deleteUnselectedIncomes(loanId,
          commonIncome != null ? commonIncome.getSelectedIncomes() : null);
    }

    return list;
  }

  @Override
  @Transactional
  public SalaryIncome save(SalaryIncome salaryIncome, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            salaryIncome.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    SalaryIncomeEntity salaryIncomeEntity = new SalaryIncomeEntity();
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    if (StringUtils.isEmpty(salaryIncome.getUuid())) {
      int count = salaryIncomeRepository.countByLoanApplicationId(
          salaryIncome.getLoanApplicationId());
      if (count >= 4) {
        throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "salary income", String.valueOf(4));
      }
    }
    if (ClientTypeEnum.LDP == clientType) {
      if (!StringUtils.isEmpty(salaryIncome.getUuid())) {
        salaryIncomeEntity = salaryIncomeRepository.findByUuid(salaryIncome.getUuid())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
                salaryIncome.getUuid()));
        //ko được thay đổi loan_application_id
        if (!salaryIncome.getLoanApplicationId()
            .equals(salaryIncomeEntity.getLoanApplicationId())) {
          throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE);
        }
      }
      salaryIncomeEntity.setLoanApplicationId(salaryIncome.getLoanApplicationId());
      salaryIncomeEntity.setOwnerType(salaryIncome.getOwnerType());
      salaryIncomeEntity.setPaymentMethod(salaryIncome.getPaymentMethod());
      salaryIncomeEntity.setOfficeName(salaryIncome.getOfficeName());
      salaryIncomeEntity.setOfficePhone(salaryIncome.getOfficePhone());
      salaryIncomeEntity.setOfficeProvince(salaryIncome.getOfficeProvince());
      salaryIncomeEntity.setOfficeProvinceName(salaryIncome.getOfficeProvinceName());
      salaryIncomeEntity.setOfficeDistrict(salaryIncome.getOfficeDistrict());
      salaryIncomeEntity.setOfficeDistrictName(salaryIncome.getOfficeDistrictName());
      salaryIncomeEntity.setOfficeWard(salaryIncome.getOfficeWard());
      salaryIncomeEntity.setOfficeWardName(salaryIncome.getOfficeWardName());
      salaryIncomeEntity.setOfficeAddress(salaryIncome.getOfficeAddress());
      salaryIncomeEntity.setOfficeTitle(salaryIncome.getOfficeTitle());
      salaryIncomeEntity.setValue(salaryIncome.getValue());
      salaryIncomeEntity.setPayerId(salaryIncome.getPayerId());
      if (StringUtils.isEmpty(salaryIncome.getUuid())
          && LoanCurrentStepEnum.LOAN_INFORMATION.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode());
        loanApplicationRepository.save(loanApplication);
      }
    } else if (ClientTypeEnum.CMS == clientType) {
      validateInput(salaryIncome);
      salaryIncomeEntity = SalaryIncomeMapper.INSTANCE.toEntity(salaryIncome);
    }
    salaryIncomeRepository.save(salaryIncomeEntity);
    return SalaryIncomeMapper.INSTANCE.toModel(salaryIncomeEntity);
  }

  @Override
  public SalaryIncome findByUuid(String uuid) {
    return SalaryIncomeMapper.INSTANCE.toModel(salaryIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }

  @Override
  public void deleteByUuid(String uuid, ClientTypeEnum clientType) {
    SalaryIncome model = findByUuid(uuid);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            model.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    salaryIncomeRepository.delete(salaryIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));

    //nếu salary-income ứng vs loanId bị xóa hết thì xóa cả thông tin cmsTabAction
    if (ClientTypeEnum.CMS == clientType) {
      List<SalaryIncomeEntity> salaryIncomeEntities = salaryIncomeRepository.findByLoanApplicationId(
          model.getLoanApplicationId());
      if (CollectionUtils.isEmpty(salaryIncomeEntities)) {
        cmsTabActionService.delete(model.getLoanApplicationId(), CMSTabEnum.SALARY_INCOME);
      }
    }
  }

  @Override
  public List<SalaryIncome> findByLoanApplicationId(String loanApplicationId) {
    return SalaryIncomeMapper.INSTANCE.toModels(
        salaryIncomeRepository.findByLoanApplicationId(loanApplicationId));
  }

  @Override
  public List<SalaryIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId) {
    return SalaryIncomeMapper.INSTANCE.toModels(
        salaryIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanApplicationId));
  }

  private void validateInput(SalaryIncome salaryIncome) {
    Map<String, String> errorDetail = new HashMap<>();
    if (CompanyEnum.MSB.equals(salaryIncome.getCompany())) {
      if (salaryIncome.getBand() == null) {
        errorDetail.put("not_blank.band", "must not be blank");
      }

      if (StringUtils.isEmpty(salaryIncome.getEmplCode())) {
        errorDetail.put("not_blank.empl_code", "must not be blank");
      }
    }
    if (salaryIncome.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      if (StringUtils.isEmpty(salaryIncome.getPayerId())) {
        errorDetail.put("not_blank.payer_id", "must not be blank");
      } else {
        loanPayerRepository.findByUuid(salaryIncome.getPayerId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
