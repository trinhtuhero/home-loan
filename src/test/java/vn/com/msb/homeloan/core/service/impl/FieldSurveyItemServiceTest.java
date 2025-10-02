package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.FieldSurveyItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.FieldSurveyItem;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.FieldSurveyItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.CMSTabActionService;
import vn.com.msb.homeloan.core.service.FieldSurveyItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class FieldSurveyItemServiceTest {

  FieldSurveyItemService fieldSurveyItemService;

  @Mock
  FieldSurveyItemRepository fieldSurveyItemRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CMSTabActionService cmsTabActionService;

  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  @BeforeEach
  void setUp() {
    this.fieldSurveyItemService = new FieldSurveyItemServiceImpl(
        fieldSurveyItemRepository,
        loanApplicationRepository,
        loanApplicationService,
        cmsTabActionService);
  }

  @Test
  void givenValidInput_ThenGetById_shouldReturnNotFound() {

    doReturn(Optional.empty()).when(fieldSurveyItemRepository).findById("uuid");

    ApplicationException exception = assertThrows(ApplicationException.class, () ->
    {
      fieldSurveyItemService.getById("uuid");
    });
    assertEquals(exception.getCode(), ErrorEnum.FIELD_SURVEY_ITEM_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenGetById_shouldReturnSuccess() {
    FieldSurveyItemEntity mockEntity = FieldSurveyItemEntity.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(mockEntity)).when(fieldSurveyItemRepository)
        .findById(mockEntity.getUuid());

    FieldSurveyItem fieldSurveyItem = fieldSurveyItemService.getById(mockEntity.getUuid());
    assertEquals(fieldSurveyItem.getUuid(), mockEntity.getUuid());
    assertEquals(fieldSurveyItem.getLoanApplicationId(), mockEntity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnSuccess() {
    FieldSurveyItemEntity mockEntity = FieldSurveyItemEntity.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build();
    List<FieldSurveyItemEntity> fieldSurveyItemEntities = new ArrayList<>();
    fieldSurveyItemEntities.add(mockEntity);

    doReturn(fieldSurveyItemEntities).when(fieldSurveyItemRepository)
        .findByLoanApplicationIdOrderByCreatedAt(mockEntity.getLoanApplicationId());

    List<FieldSurveyItem> fieldSurveyItems = fieldSurveyItemService.getByLoanId(
        mockEntity.getLoanApplicationId());
    assertEquals(fieldSurveyItems.size(), 1);
    assertEquals(fieldSurveyItems.get(0).getLoanApplicationId(), mockEntity.getLoanApplicationId());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnNotFound() {

    doReturn(Optional.empty()).when(fieldSurveyItemRepository).findById("uuid");

    ApplicationException exception = assertThrows(ApplicationException.class, () ->
    {
      fieldSurveyItemService.deleteById("uuid");
    });
    assertEquals(exception.getCode(), ErrorEnum.FIELD_SURVEY_ITEM_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnLoanNotFound() {

    FieldSurveyItemEntity mockEntity = FieldSurveyItemEntity.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build();

    doReturn(Optional.of(mockEntity)).when(fieldSurveyItemRepository)
        .findById(mockEntity.getUuid());

    doReturn(Optional.empty()).when(loanApplicationRepository)
        .findById(mockEntity.getLoanApplicationId());

    ApplicationException exception = assertThrows(ApplicationException.class, () ->
    {
      fieldSurveyItemService.deleteById("uuid");
    });
    assertEquals(exception.getCode(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteById_shouldReturnSuccess() throws IOException {
    FieldSurveyItemEntity mockEntity = FieldSurveyItemEntity.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build();
    doReturn(Optional.of(mockEntity)).when(fieldSurveyItemRepository)
        .findById(mockEntity.getUuid());

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication("profileId"));
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository)
        .findById(mockEntity.getLoanApplicationId());

    fieldSurveyItemService.deleteById("uuid");
    verify(fieldSurveyItemRepository, times(1)).deleteById(any());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnLoanNotFound() {

    doReturn(Optional.empty()).when(loanApplicationRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () ->
    {
      fieldSurveyItemService.saves(new ArrayList<>(), LOAN_ID);
    });
    assertEquals(exception.getCode(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSaves_shouldReturnSuccess() {
    FieldSurveyItemEntity mockEntity = FieldSurveyItemEntity.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build();
    doReturn(mockEntity).when(fieldSurveyItemRepository).save(any());
    doReturn(Optional.of(mockEntity)).when(fieldSurveyItemRepository).findById(any());

    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication("profileId"));
    loanApplication.setUuid(LOAN_ID);
    doReturn(Optional.of(loanApplication)).when(loanApplicationRepository).findById(LOAN_ID);

    List<FieldSurveyItem> requests = new ArrayList<>();
    requests.add(FieldSurveyItem.builder()
        .uuid("uuid1")
        .loanApplicationId(LOAN_ID)
        .build());
    requests.add(FieldSurveyItem.builder()
        .uuid("uuid")
        .loanApplicationId(LOAN_ID)
        .build());

    List<FieldSurveyItem> fieldSurveyItems = fieldSurveyItemService.saves(requests, LOAN_ID);

    assertEquals(fieldSurveyItems.size(), 2);
  }

}