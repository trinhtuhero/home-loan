package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.entity.OtherEvaluateEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CommonIncome;
import vn.com.msb.homeloan.core.model.OtherEvaluate;
import vn.com.msb.homeloan.core.model.mapper.OtherEvaluateMapper;
import vn.com.msb.homeloan.core.repository.LoanPayerRepository;
import vn.com.msb.homeloan.core.repository.OtherEvaluateRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.CommonIncomeService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.OtherEvaluateService;
import vn.com.msb.homeloan.core.util.StringUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class OtherEvaluateServiceImpl implements OtherEvaluateService {

  private final OtherEvaluateRepository otherEvaluateRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;
  private final LoanPayerRepository loanPayerRepository;

  private final CommonIncomeService commonIncomeService;

  @Override
  @Transactional
  public List<OtherEvaluate> saves(List<OtherEvaluate> otherEvaluates) {
    if (CollectionUtils.isEmpty(otherEvaluates)) {
      return new ArrayList<>();
    }

    if (otherEvaluates.size() > 1) {
      throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "other evaluate", String.valueOf(1));
    }

    String loanId = otherEvaluates.get(0).getLoanApplicationId();

    CommonIncome commonIncome = commonIncomeService.findByLoanApplicationId(loanId);

    List<OtherEvaluate> otherEvaluateSave = new ArrayList<>();
    for (OtherEvaluate otherEvaluate : otherEvaluates) {
      validateInput(otherEvaluate);
      otherEvaluateSave.add(save(otherEvaluate));
    }
    // delete unselected incomes
    loanApplicationService.deleteUnselectedIncomes(loanId,
        commonIncome != null ? commonIncome.getSelectedIncomes() : null);
    // save tabAction
    cmsTabActionService.save(loanId, CMSTabEnum.ASSUMING_OTHERS_INCOME);

    return otherEvaluateSave;
  }

  @Transactional
  private OtherEvaluate save(OtherEvaluate otherEvaluate) {
    OtherEvaluateEntity entitySave = OtherEvaluateMapper.INSTANCE.toEntity(otherEvaluate);
    // insert
    if (StringUtils.isEmpty(otherEvaluate.getUuid())) {
      List<OtherEvaluateEntity> otherEvaluateEntitiesInDB = otherEvaluateRepository.findByLoanApplicationId(
          otherEvaluate.getLoanApplicationId());
      // đã tồn tại
      if (otherEvaluateEntitiesInDB.size() > 0) {
        throw new ApplicationException(ErrorEnum.ITEM_LIMIT, "other evaluate", String.valueOf(1));
      }
    } else {
      otherEvaluateRepository.findById(otherEvaluate.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.RESOURCE_NOT_FOUND, "uuid",
              otherEvaluate.getUuid()));
    }
    otherEvaluateRepository.save(entitySave);

    return OtherEvaluateMapper.INSTANCE.toModel(entitySave);
  }

  @Override
  public void deleteByUuid(String uuid) {
    OtherEvaluateEntity otherEvaluateEntity = otherEvaluateRepository.findByUuid(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.OTHER_EVALUATE_NOT_FOUND));
    otherEvaluateRepository.delete(otherEvaluateEntity);
    cmsTabActionService.delete(otherEvaluateEntity.getLoanApplicationId(),
        CMSTabEnum.ASSUMING_OTHERS_INCOME);
  }

  private void validateInput(OtherEvaluate otherEvaluate) {
    Map<String, String> errorDetail = new HashMap<>();
    if (otherEvaluate.getOwnerType().equals(OwnerTypeEnum.PAYER_PERSON)) {
      if (StringUtils.isEmpty(otherEvaluate.getPayerId())) {
        errorDetail.put("not_blank.payer_id", "must not be blank");
      } else {
        loanPayerRepository.findByUuid(otherEvaluate.getPayerId())
            .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_PAYER_NOT_FOUND));
      }
    }
    if (!errorDetail.isEmpty()) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM, errorDetail);
    }
  }
}
