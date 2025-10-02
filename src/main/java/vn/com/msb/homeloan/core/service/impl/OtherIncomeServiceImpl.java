package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.mapper.LandTransactionMapper;
import vn.com.msb.homeloan.api.dto.response.LandTransactionResponse;
import vn.com.msb.homeloan.core.constant.*;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.OtherIncomeEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.OtherIncome;
import vn.com.msb.homeloan.core.model.mapper.OtherIncomeMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OtherIncomeRepository;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.*;

@Slf4j
@Service
@AllArgsConstructor
public class OtherIncomeServiceImpl implements OtherIncomeService {

  private final OtherIncomeRepository otherIncomeRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final LoanPayerRepository loanPayerRepository;

  private final CommonIncomeService commonIncomeService;

  private final LandTransactionService landTransactionService;

  @Override
  public OtherIncome findByUuid(String uuid) {
    return OtherIncomeMapper.INSTANCE.toModel(otherIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));
  }

  @Override
  public void deleteByUuid(String uuid, ClientTypeEnum clientType) {
    OtherIncome model = findByUuid(uuid);
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            model.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);

    if (IncomeFromEnum.GOM_XAY.equals(model.getIncomeFrom())) {
      landTransactionService.deleteByOtherIncomeId(uuid);
    }
    otherIncomeRepository.delete(otherIncomeRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid)));

    //nếu other-income ứng vs loanId bị xóa hết thì xóa cả thông tin cmsTabAction
    if (ClientTypeEnum.CMS == clientType) {
      List<OtherIncomeEntity> otherIncomeEntities = otherIncomeRepository.findByLoanApplicationId(
          model.getLoanApplicationId());
      if (CollectionUtils.isEmpty(otherIncomeEntities)) {
        cmsTabActionService.delete(model.getLoanApplicationId(), CMSTabEnum.OTHERS_INCOME);
      }
    }
  }

  @Override
  public List<OtherIncome> findByLoanApplicationId(String loanApplicationId) {
    return OtherIncomeMapper.INSTANCE.toModels(
        otherIncomeRepository.findByLoanApplicationId(loanApplicationId));
  }

  @Override
  public List<OtherIncome> findByLoanApplicationIdOrderByCreatedAtAsc(String loanApplicationId) {
    return OtherIncomeMapper.INSTANCE.toModels(
        otherIncomeRepository.findByLoanApplicationIdOrderByCreatedAtAsc(loanApplicationId));
  }

  @Override
  @Transactional
  public List<OtherIncome> saves(List<OtherIncome> otherIncomes) {
    if (CollectionUtils.isEmpty(otherIncomes)) {
      throw new ApplicationException(ErrorEnum.SALARY_INCOME_NOT_EMPTY);
    }
    if (otherIncomes.size() > 6) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "other income", String.valueOf(6));
    }
    List<OtherIncome> otherIncomeResult = new ArrayList<>();
    for (OtherIncome otherIncome : otherIncomes) {
      validateInput(otherIncome);
      otherIncomeResult.add(save(otherIncome, ClientTypeEnum.CMS));
    }
    if (!CollectionUtils.isEmpty(otherIncomes)) {
      String loanId = otherIncomes.get(0).getLoanApplicationId();

      CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

      // delete unselected incomes
      loanApplicationService.deleteUnselectedIncomes(loanId,
          commonIncome != null ? commonIncome.getSelectedIncomes() : null);
      // save tabAction
      cmsTabActionService.save(loanId, CMSTabEnum.OTHERS_INCOME);
    }
    return otherIncomeResult;
  }

  @Override
  @Transactional
  public OtherIncome save(OtherIncome otherIncome, ClientTypeEnum clientType) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(
            otherIncome.getLoanApplicationId())
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    OtherIncomeEntity otherIncomeEntity = new OtherIncomeEntity();
    loanApplicationService.checkEditLoanApp(loanApplication, clientType);
    //Giới hạn 06 bản ghi
    if (StringUtils.isEmpty(otherIncome.getUuid())) {
      checkLimitIncome(otherIncome);
    } else {
      otherIncomeEntity = otherIncomeRepository.findById(otherIncome.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
              otherIncome.getUuid()));

      List<String> others = Arrays.asList(IncomeFromEnum.OTHERS.getCode(),
          IncomeFromEnum.SAVING_ACCOUNTS.getCode());

      if (otherIncomeEntity.getIncomeFrom() != null && otherIncome.getIncomeFrom() != null
          && !otherIncomeEntity.getIncomeFrom().equals(otherIncome.getIncomeFrom())
          && !others.contains(otherIncomeEntity.getIncomeFrom().getCode())
          && !others.contains(otherIncome.getIncomeFrom().getCode())) {
        checkLimitIncome(otherIncome);
      }
    }

    if (ClientTypeEnum.LDP == clientType) {
      otherIncomeEntity.setLoanApplicationId(otherIncome.getLoanApplicationId());
      otherIncomeEntity.setOwnerType(otherIncome.getOwnerType());
      otherIncomeEntity.setIncomeFrom(otherIncome.getIncomeFrom());
      otherIncomeEntity.setValue(otherIncome.getValue());
      otherIncomeEntity.setPayerId(otherIncome.getPayerId());
      if (StringUtils.isEmpty(otherIncome.getUuid())
          && LoanCurrentStepEnum.LOAN_INFORMATION.getCode()
          .equalsIgnoreCase(loanApplication.getCurrentStep())) {
        loanApplication.setCurrentStep(LoanCurrentStepEnum.LOAN_INCOME.getCode());
        loanApplicationRepository.save(loanApplication);
      }
    } else if (ClientTypeEnum.CMS == clientType) {
      otherIncomeEntity = OtherIncomeMapper.INSTANCE.toEntity(otherIncome);
    }
    if (IncomeFromEnum.GOM_XAY.equals(otherIncome.getIncomeFrom())) {
      otherIncomeEntity.setIncomePerMonth(otherIncome.getIncomePerMonth());
      otherIncomeEntity.setQuantity(otherIncome.getQuantity());
    }
    OtherIncomeEntity result = otherIncomeRepository.save(otherIncomeEntity);
    log.info("save OtherIncome successful: {}", result);
    OtherIncome response = OtherIncomeMapper.INSTANCE.toModel(otherIncomeEntity);
    if (IncomeFromEnum.GOM_XAY.equals(otherIncome.getIncomeFrom())) {
      if (otherIncome.getLandTransactions() != null && !otherIncome.getLandTransactions()
          .isEmpty()) {
        List<LandTransactionResponse> landTransactionResponses = landTransactionService.saveOrUpdate(
            otherIncome.getLandTransactions(), result.getUuid());
        response.setLandTransactions(
            LandTransactionMapper.INSTANCE.responseToRequest(landTransactionResponses));
      }
      log.info("OtherIncome response: {}", response);
    }
    return response;
  }

  private void checkLimitIncome(OtherIncome otherIncome) {
    List<String> incomeFromIds = Collections.singletonList(
        IncomeFromEnum.RENTAL_PROPERTIES.getCode());
    int maxIncome = 4;
    String message = "other income from Property leasing (cars, real estate)";
    if (!IncomeFromEnum.RENTAL_PROPERTIES.equals(otherIncome.getIncomeFrom())
        && !IncomeFromEnum.GOM_XAY.equals(otherIncome.getIncomeFrom())) {
      incomeFromIds = Arrays.asList(IncomeFromEnum.OTHERS.getCode(),
          IncomeFromEnum.SAVING_ACCOUNTS.getCode());
      maxIncome = 2;
      message = "Interest on savings accounts and other sources of income";
    } else if (IncomeFromEnum.GOM_XAY.equals(otherIncome.getIncomeFrom())) {
      maxIncome = 1;
      message = "other income from land transaction";
      incomeFromIds = Collections.singletonList(IncomeFromEnum.GOM_XAY.getCode());
    }

    int countOtherIncome = otherIncomeRepository.countByLoanIdEndIncomeFromIds(
        otherIncome.getLoanApplicationId(), incomeFromIds);
    if (countOtherIncome >= maxIncome) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, message,
          String.valueOf(maxIncome));
    }
  }

  private void validateInput(OtherIncome otherIncome) {
    Map<String, String> errorDetail = new HashMap<>();
    if (otherIncome.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      if (StringUtils.isEmpty(otherIncome.getPayerId())) {
        errorDetail.put("not_blank.payer_id", "must not be blank");
      } else {
        loanPayerRepository.findByUuid(otherIncome.getPayerId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
      }
    }
    if (IncomeFromEnum.RENTAL_PROPERTIES.equals(otherIncome.getIncomeFrom())) {
      if (StringUtils.isEmpty(otherIncome.getRentProperty())) {
        errorDetail.put("not_blank.rent_property", "must not be blank");
      }
      if (StringUtils.isEmpty(otherIncome.getTenant())) {
        errorDetail.put("not_blank.tenant", "must not be blank");
      }
      if (StringUtils.isEmpty(otherIncome.getTenantPhone())) {
        errorDetail.put("not_blank.tenant_phone", "must not be blank");
      }
      if (StringUtils.isEmpty(otherIncome.getTenantPurpose())) {
        errorDetail.put("not_blank.tenant_purpose", "must not be blank");
      }
      if (otherIncome.getTenantPrice() == null) {
        errorDetail.put("not_blank.tenant_price", "must not be null");
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
