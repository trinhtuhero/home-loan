package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.AssetEvaluateEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.AssetEvaluate;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.mapper.AssetEvaluateMapper;
import vn.com.msb.homeloan.core.repository.AssetEvaluateRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.service.*;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class AssetEvaluateServiceImpl implements AssetEvaluateService {

  private final LoanApplicationRepository loanApplicationRepository;
  private final AssetEvaluateRepository assetEvaluateRepository;
  private final AssetEvaluateItemService assetEvaluateItemService;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final LoanPayerRepository loanPayerRepository;

  private final CommonIncomeService commonIncomeService;

  @Override
  public AssetEvaluateEntity findByUuid(String uuid) {
    return assetEvaluateRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid", uuid));
  }

  @Transactional
  @Override
  public AssetEvaluate save(AssetEvaluate assetEvaluate) {
    String loanId = assetEvaluate.getLoanApplicationId();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);

    validateInput(assetEvaluate);

    if (assetEvaluate.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      loanPayerRepository.findByUuid(assetEvaluate.getPayerId())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
    }

    if (assetEvaluate.getAssetEvaluateItems().size() > 10) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "asset evaluate item",
          String.valueOf(10));
    }

    AssetEvaluateEntity assetEvaluateEntity;
    if (!StringUtils.isEmpty(assetEvaluate.getUuid())) {
      assetEvaluateEntity = findByUuid(assetEvaluate.getUuid());
    } else {
      assetEvaluateEntity = assetEvaluateRepository.findByLoanApplicationId(loanId);
    }
    AssetEvaluateEntity assetEvaluateEntitySave = AssetEvaluateMapper.INSTANCE.toEntity(
        assetEvaluate);
    if (assetEvaluateEntity != null) {
      assetEvaluateEntitySave.setUuid(assetEvaluateEntity.getUuid());
    } else {
      assetEvaluate.getAssetEvaluateItems().forEach(aei -> aei.setUuid(null));
    }
    AssetEvaluate result = AssetEvaluateMapper.INSTANCE.toModel(
        assetEvaluateRepository.save(assetEvaluateEntitySave));
    assetEvaluate.getAssetEvaluateItems()
        .forEach(aei -> aei.setAssetEvalId(assetEvaluateEntitySave.getUuid()));
    List<AssetEvaluateItem> assetEvaluateItems = assetEvaluateItemService.save(
        assetEvaluate.getAssetEvaluateItems());
    result.setAssetEvaluateItems(assetEvaluateItems);

    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

    // delete unselected incomes
    loanApplicationService.deleteUnselectedIncomes(loanId,
        commonIncome != null ? commonIncome.getSelectedIncomes() : null);
    // save tabAction
    cmsTabActionService.save(loanId, CMSTabEnum.ASSUMING_TOTAL_ASSETS_INCOME);

    return result;
  }

  private void validateInput(AssetEvaluate assetEvaluate) {
    Map<String, String> errorDetail = new HashMap<>();
    if (assetEvaluate.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      if (StringUtils.isEmpty(assetEvaluate.getPayerId())) {
        errorDetail.put("not_blank.payer_id", "must not be blank");
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
