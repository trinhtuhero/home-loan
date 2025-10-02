package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.BusinessIncomeEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.BusinessIncome;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.mapper.BusinessIncomeMapper;
import vn.com.msb.homeloan.core.repository.BusinessIncomeRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.service.BusinessIncomeService;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class BusinessIncomeServiceImpl implements BusinessIncomeService {

  private final BusinessIncomeRepository businessIncomeRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final LoanPayerRepository loanPayerRepository;

  private final CommonIncomeService commonIncomeService;

  @Override
  public BusinessIncome findByUuid(String uuid) {
    return BusinessIncomeMapper.INSTANCE.toModel(businessIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }

  @Override
  @Transactional
  public List<BusinessIncome> saves(List<BusinessIncome> businessIncomes) {
    if (CollectionUtils.isEmpty(businessIncomes)) {
      throw new ApplicationException(ErrorEnum.BUSINESS_INCOME_NOT_EMPTY);
    }
    if (businessIncomes.size() > 2) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "business income", String.valueOf(2));
    }
    List<BusinessIncome> businessIncomeResult = new ArrayList<>();
    for (BusinessIncome businessIncome : businessIncomes) {
      validateInput(businessIncome);
      businessIncomeResult.add(save(businessIncome, ClientTypeEnum.CMS));
    }

    // Nếu danh sách khác rỗng
    if (!CollectionUtils.isEmpty(businessIncomes)) {
      String loanId = businessIncomes.get(0).getLoanApplicationId();

      CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);
      // delete unselected incomes
      loanApplicationService.deleteUnselectedIncomes(loanId,
          commonIncome != null ? commonIncome.getSelectedIncomes() : null);

      if (BusinessTypeEnum.ENTERPRISE.equals(businessIncomes.get(0).getBusinessType())) {
        cmsTabActionService.save(loanId, CMSTabEnum.BUSINESS_INCOME);
      } else {
        cmsTabActionService.save(loanId, CMSTabEnum.PERSONAL_BUSINESS_INCOME);
      }
    }
    return businessIncomeResult;
  }

  @Override
  @Transactional
  public BusinessIncome save(BusinessIncome businessIncome, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            businessIncome.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    BusinessIncomeEntity businessIncomeEntity = new BusinessIncomeEntity();
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    //Giới hạn 02 bản ghi
    if (StringUtils.isEmpty(businessIncome.getUuid())) {
      checkLimitIncome(businessIncome);
    } else {
      businessIncomeEntity = businessIncomeRepository.findById(businessIncome.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
              businessIncome.getUuid()));
      //ko được thay đổi loan_application_id
      if (!businessIncome.getLoanApplicationId()
          .equals(businessIncomeEntity.getLoanApplicationId())) {
        throw new ApplicationException(ErrorEnum.LOAN_APPLICATION_ID_CAN_NOT_CHANGE);
      }

      List<String> hkds = Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION.getCode(),
          BusinessTypeEnum.WITHOUT_REGISTRATION.getCode());
      if (businessIncomeEntity.getBusinessType() != null && businessIncome.getBusinessType() != null
          && !businessIncomeEntity.getBusinessType().equals(businessIncome.getBusinessType())
          && !hkds.contains(businessIncomeEntity.getBusinessType().getCode())
          && !hkds.contains(businessIncome.getBusinessType().getCode())) {
        checkLimitIncome(businessIncome);
      }
    }
    if (ClientTypeEnum.LDP == clientType) {
      businessIncomeEntity.setLoanApplicationId(businessIncome.getLoanApplicationId());
      businessIncomeEntity.setOwnerType(businessIncome.getOwnerType());
      businessIncomeEntity.setBusinessType(businessIncome.getBusinessType());
      businessIncomeEntity.setBusinessLine(businessIncome.getBusinessLine());
      businessIncomeEntity.setBusinessCode(businessIncome.getBusinessCode());
      businessIncomeEntity.setName(businessIncome.getName());
      businessIncomeEntity.setProvince(businessIncome.getProvince());
      businessIncomeEntity.setProvinceName(businessIncome.getProvinceName());
      businessIncomeEntity.setDistrict(businessIncome.getDistrict());
      businessIncomeEntity.setDistrictName(businessIncome.getDistrictName());
      businessIncomeEntity.setWard(businessIncome.getWard());
      businessIncomeEntity.setWardName(businessIncome.getWardName());
      businessIncomeEntity.setAddress(businessIncome.getAddress());
      businessIncomeEntity.setPhone(businessIncome.getPhone());
      businessIncomeEntity.setValue(businessIncome.getValue());
      businessIncomeEntity.setPayerId(businessIncome.getPayerId());
      if (StringUtils.isEmpty(businessIncome.getUuid())
          && LoanCurrentStepEnum.LOAN_INFORMATION.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode());
        loanApplicationRepository.save(loanApplication);
      }
    } else if (ClientTypeEnum.CMS == clientType) {
      if (businessIncome.getRevenue() != null && businessIncome.getCost() != null) {
        long profit = businessIncome.getRevenue() - businessIncome.getCost();
        BigDecimal profitMargin = (BigDecimal.valueOf(profit * 100)).divide(
            BigDecimal.valueOf(businessIncome.getRevenue()), 2, RoundingMode.HALF_UP);
        businessIncome.setProfit(profit);
        businessIncome.setProfitMargin(profitMargin);
      }
      businessIncomeEntity = BusinessIncomeMapper.INSTANCE.toEntity(businessIncome);
    }
    businessIncomeRepository.save(businessIncomeEntity);
    return BusinessIncomeMapper.INSTANCE.toModel(businessIncomeEntity);
  }

  private void checkLimitIncome(BusinessIncome businessIncome) {
    List<BusinessTypeEnum> businessTypes = Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION,
        BusinessTypeEnum.WITHOUT_REGISTRATION);
    if (BusinessTypeEnum.ENTERPRISE.equals(businessIncome.getBusinessType())) {
      businessTypes = Arrays.asList(BusinessTypeEnum.ENTERPRISE);
    }
    int countBusinessIncome = businessIncomeRepository.countByLoanApplicationIdAndBusinessTypeIn(
        businessIncome.getLoanApplicationId(), businessTypes);
    if (countBusinessIncome >= 2) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "business income", String.valueOf(2));
    }
  }

  @Override
  public void deleteByUuid(String uuid, ClientTypeEnum clientType) {
    BusinessIncome model = findByUuid(uuid);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            model.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    businessIncomeRepository.delete(businessIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));

    //nếu business ứng vs loanId bị xóa hết thì xóa cả thông tin cmsTabAction
    if (ClientTypeEnum.CMS == clientType) {
      if (model.getBusinessType().equals(BusinessTypeEnum.ENTERPRISE)) {
        List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdAndBusinessTypeIn(
            model.getLoanApplicationId(), Arrays.asList(BusinessTypeEnum.ENTERPRISE));
        if (CollectionUtils.isEmpty(businessIncomeEntities)) {
          cmsTabActionService.delete(model.getLoanApplicationId(), CMSTabEnum.BUSINESS_INCOME);
        }
      } else {
        List<BusinessIncomeEntity> businessIncomeEntities = businessIncomeRepository.findByLoanApplicationIdAndBusinessTypeIn(
            model.getLoanApplicationId(), Arrays.asList(BusinessTypeEnum.WITH_REGISTRATION,
                BusinessTypeEnum.WITHOUT_REGISTRATION));
        if (CollectionUtils.isEmpty(businessIncomeEntities)) {
          cmsTabActionService.delete(model.getLoanApplicationId(),
              CMSTabEnum.PERSONAL_BUSINESS_INCOME);
        }
      }
    }
  }

  @Override
  public List<BusinessIncome> findByLoanApplicationId(String loanApplicationId) {
    return BusinessIncomeMapper.INSTANCE.toModels(
        businessIncomeRepository.findByLoanApplicationId(loanApplicationId));
  }

  @Override
  public List<BusinessIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId) {
    return BusinessIncomeMapper.INSTANCE.toModels(
        businessIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanApplicationId));
  }

  private void validateInput(BusinessIncome businessIncome) {
    Map<String, String> errorDetail = new HashMap<>();
    if (businessIncome.getBusinessType().equals(BusinessTypeEnum.WITH_REGISTRATION)
        || businessIncome.getBusinessType().equals(BusinessTypeEnum.ENTERPRISE)) {
      if (StringUtils.isEmpty(businessIncome.getBusinessCode())) {
        errorDetail.put("not_blank.business_code", "must not be blank");
      }
      if (businessIncome.getIssuedDate() == null) {
        errorDetail.put("not_null.issued_date", "must not be null");
      }
      if (businessIncome.getBusinessType().equals(BusinessTypeEnum.WITH_REGISTRATION)) {
        if (StringUtils.isEmpty(businessIncome.getPlaceOfIssue())) {
          errorDetail.put("not_blank.place_of_issue", "must not be blank");
        }
      }
    } else if (businessIncome.getBusinessType().equals(BusinessTypeEnum.WITHOUT_REGISTRATION)) {
      if (businessIncome.getBusinessStartDate() == null) {
        errorDetail.put("not_null.business_start_date", "must not be null");
      }
    }
    if (businessIncome.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      if (StringUtils.isEmpty(businessIncome.getPayerId())) {
        errorDetail.put("not_blank.payer_id", "must not be blank");
      } else {
        loanPayerRepository.findByUuid(businessIncome.getPayerId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
