package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import vn.com.msb.homeloan.core.constant.CMSTabEnum;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;
import vn.com.msb.homeloan.core.model.mapper.FieldSurveyItemMapper;
import vn.com.msb.homeloan.core.repository.FieldSurveyItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.FieldSurveyItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.emptyIfNull;

@Slf4j
@Service
@AllArgsConstructor
public class FieldSurveyItemServiceImpl implements FieldSurveyItemService {

  private final FieldSurveyItemRepository fieldSurveyItemRepository;
  private final LoanApplicationRepository loanApplicationRepository;
  private final LoanApplicationService loanApplicationService;
  private final CMSTabActionService cmsTabActionService;

  @Override
  @Transactional
  public List<FieldSurveyItem> saves(List<FieldSurveyItem> fieldSurveyItems, String loanId) {
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));
    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);
    List<FieldSurveyItem> results = new ArrayList<>();
    emptyIfNull(fieldSurveyItems).forEach(fieldSurveyItem -> {
      fieldSurveyItem.setLoanApplicationId(loanApplication.getUuid());
      results.add(save(fieldSurveyItem));
    });

    return results;
  }

  @Override
  public FieldSurveyItem save(FieldSurveyItem fieldSurveyItem) {
    if (!StringUtils.isEmpty(fieldSurveyItem.getUuid())) {
      fieldSurveyItemRepository.findById(fieldSurveyItem.getUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.FIELD_SURVEY_ITEM_NOT_FOUND));
    }
    FieldSurveyItemEntity entity = fieldSurveyItemRepository.save(
        FieldSurveyItemMapper.INSTANCE.toEntity(fieldSurveyItem));
    return FieldSurveyItemMapper.INSTANCE.toModel(entity);
  }

  @Override
  public FieldSurveyItem getById(String uuid) {
    FieldSurveyItemEntity entity = fieldSurveyItemRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.FIELD_SURVEY_ITEM_NOT_FOUND));
    return FieldSurveyItemMapper.INSTANCE.toModel(entity);
  }

  @Override
  public List<FieldSurveyItem> getByLoanId(String loanId) {
    List<FieldSurveyItemEntity> originFieldSurveyItems = fieldSurveyItemRepository.findByLoanApplicationIdOrderByCreatedAt(
        loanId);
    return FieldSurveyItemMapper.INSTANCE.toModels(originFieldSurveyItems);
  }

  @Override
  public void deleteById(String uuid) {
    FieldSurveyItemEntity entity = fieldSurveyItemRepository.findById(uuid)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.FIELD_SURVEY_ITEM_NOT_FOUND));
    String loanId = entity.getLoanApplicationId();
    LoanApplicationEntity loanApplication = loanApplicationRepository.findById(loanId)
        .orElseThrow(() -> new ApplicationException(ErrorEnum.LOAN_APPLICATION_NOT_FOUND));

    loanApplicationService.checkEditLoanApp(loanApplication, ClientTypeEnum.CMS);
    fieldSurveyItemRepository.deleteById(uuid);

    // Xóa tích xanh
    List<FieldSurveyItemEntity> fieldSurveyItemEntities = fieldSurveyItemRepository.findByLoanApplicationId(
        loanId);
    if (CollectionUtils.isEmpty(fieldSurveyItemEntities)) {
      cmsTabActionService.delete(loanId, CMSTabEnum.EXPERTISE);
    }
  }
}
