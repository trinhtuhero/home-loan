package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.ExceptionItemEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ExceptionItem;
import vn.com.msb.homeloan.core.model.mapper.ExceptionItemMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.ExceptionItemRepository;
import vn.com.msb.homeloan.core.repository.LoanApplicationRepository;
import vn.com.msb.homeloan.core.service.ExceptionItemService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class ExceptionItemServiceTest {

  private final String EXCEPTION_ITEM_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String CREDIT_APPRAISAL_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";

  ExceptionItemService exceptionItemService;

  @Mock
  ExceptionItemRepository exceptionItemRepository;

  @Mock
  LoanApplicationRepository loanApplicationRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @BeforeEach
  void setUp() {
    this.exceptionItemService = new ExceptionItemServiceImpl(
        exceptionItemRepository,
        loanApplicationRepository,
        loanApplicationService);
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSuccess() {
    ExceptionItem exceptionItem = ExceptionItem.builder()
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(1).when(exceptionItemRepository).countByLoanApplicationId(LOAN_ID);

    ExceptionItemEntity entity = ExceptionItemEntity.builder().loanApplicationId(LOAN_ID).build();

    doReturn(entity).when(exceptionItemRepository)
        .save(ExceptionItemMapper.INSTANCE.toEntity(exceptionItem));

    ExceptionItem result = exceptionItemService.save(exceptionItem);

    assertEquals(result.getLoanApplicationId(), LOAN_ID);
  }

  @Test
  void givenValidInput_ThenGetByLoanId_shouldReturnSuccess() {
    ExceptionItemEntity exceptionItemEntity = ExceptionItemEntity.builder()
        .uuid(EXCEPTION_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();

    doReturn(Arrays.asList(exceptionItemEntity)).when(exceptionItemRepository)
        .findByLoanApplicationIdOrderByCreatedAtAsc(LOAN_ID);

    List<ExceptionItem> result = exceptionItemService.getByLoanId(LOAN_ID);

    assertEquals(result.get(0).getUuid(), EXCEPTION_ITEM_ID);
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnFailLoanApplicationNotFound() {
    ExceptionItem exceptionItem = ExceptionItem.builder()
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      exceptionItemService.save(exceptionItem);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.LOAN_APPLICATION_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnFailExceptionItemLimit() {
    ExceptionItem exceptionItem = ExceptionItem.builder()
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();

    LoanApplicationEntity loanApplicationEntity = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));
    doReturn(Optional.of(loanApplicationEntity)).when(loanApplicationRepository).findById(LOAN_ID);
    doReturn(10).when(exceptionItemRepository).countByLoanApplicationId(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      exceptionItemService.save(exceptionItem);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.ITEM_LIMIT.getCode());
  }

  @Test
  void givenValidInput_ThenDelete_shouldReturnSuccess() {
    ExceptionItem exceptionItem = ExceptionItem.builder()
        .uuid(EXCEPTION_ITEM_ID)
        .loanApplicationId(LOAN_ID)
        .creditAppraisalId(CREDIT_APPRAISAL_ID).build();
    ExceptionItemEntity exceptionItemEntity = ExceptionItemMapper.INSTANCE.toEntity(exceptionItem);
    doReturn(Optional.of(exceptionItemEntity)).when(exceptionItemRepository)
        .findById(EXCEPTION_ITEM_ID);

    exceptionItemService.deleteByUuid(EXCEPTION_ITEM_ID);

    verify(exceptionItemRepository, times(1)).delete(exceptionItemEntity);
  }
}
