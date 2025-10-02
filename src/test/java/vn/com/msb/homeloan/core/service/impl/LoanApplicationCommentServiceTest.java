package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationCommentMapper;
import vn.com.msb.homeloan.core.model.mapper.LoanApplicationMapper;
import vn.com.msb.homeloan.core.repository.LoanApplicationCommentRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.LoanApplicationCommentService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;

@ExtendWith(MockitoExtension.class)
class LoanApplicationCommentServiceTest {

  private final String UUID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String LOAN_ID = "03f9e024-7ec4-4ed3-8f1f-330d232b6399";
  private final String EMAIL = "abc@gmail.com";
  private final String CODE = "123abc";

  LoanApplicationCommentService loanApplicationCommentService;

  @Mock
  LoanApplicationCommentRepository loanApplicationCommentRepository;

  @Mock
  LoanApplicationService loanApplicationService;

  @Mock
  CmsUserService cmsUserService;

  @Mock
  CacheManager cacheManager;

  @BeforeEach
  void setUp() {
    this.loanApplicationCommentService = new LoanApplicationCommentServiceImpl(
        loanApplicationCommentRepository,
        loanApplicationService,
        cmsUserService,
        null,
        null,
        cacheManager,
        null
    );
  }

  @Test
  void givenValidInput_ThenSave_shouldReturnSucess() {
    LoanApplicationCommentEntity entity = LoanApplicationCommentEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .status(CommentStatusEnum.DRAFT)
        .build();
    CmsUser cmsUser = CmsUser.builder()
        .email(EMAIL)
        .build();
    LoanApplicationEntity loanApplication = LoanApplicationMapper.INSTANCE.toEntity(
        BuildData.buildLoanApplication(LOAN_ID));

    doReturn(LoanApplicationMapper.INSTANCE.toModel(loanApplication)).when(loanApplicationService)
        .findById(LOAN_ID);
    doReturn(cmsUser).when(cmsUserService).findByEmail(EMAIL);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);

      doReturn(LoanApplicationCommentEntity.builder()
          .comment("Test")
          .emplId("123")
          .build()).when(loanApplicationCommentRepository).save(any());

      Cache cache = Mockito.mock(Cache.class);
      Mockito.when(cacheManager.getCache(Mockito.anyString()))
          .thenReturn(cache);

      loanApplicationCommentService.save(LoanApplicationCommentMapper.INSTANCE.toModel(entity));
      verify(loanApplicationCommentRepository, times(1)).save(entity);
    }
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnResaurceEditNotAllow() {
    LoanApplicationCommentEntity entity = LoanApplicationCommentEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .status(CommentStatusEnum.SUBMITTED_BY_CUSTOMER)
        .build();

    doReturn(Optional.of(entity)).when(loanApplicationCommentRepository).findById(UUID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationCommentService.update(LoanApplicationCommentMapper.INSTANCE.toModel(entity));
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_EDIT_NOT_ALLOW.getCode());
  }

  @Test
  void givenValidInput_ThenUpdate_shouldReturnSuccess() {
    LoanApplicationCommentEntity entity = LoanApplicationCommentEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .status(CommentStatusEnum.DRAFT)
        .build();
    CmsUser cmsUser = CmsUser.builder()
        .email(EMAIL)
        .build();

    doReturn(Optional.of(entity)).when(loanApplicationCommentRepository).findById(UUID);
    doReturn(cmsUser).when(cmsUserService).findByEmail(EMAIL);

    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);

      Cache cache = Mockito.mock(Cache.class);
      Mockito.when(cacheManager.getCache(Mockito.anyString()))
          .thenReturn(cache);

      doReturn(LoanApplicationCommentEntity.builder()
          .comment("Test")
          .emplId("123")
          .build()).when(loanApplicationCommentRepository).save(any());

      loanApplicationCommentService.update(LoanApplicationCommentMapper.INSTANCE.toModel(entity));
      verify(loanApplicationCommentRepository, times(1)).save(entity);
    }
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnResourceEditNotAllow() {
    LoanApplicationCommentEntity entity = LoanApplicationCommentEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .status(CommentStatusEnum.SUBMITTED_BY_CUSTOMER)
        .build();

    doReturn(Optional.of(entity)).when(loanApplicationCommentRepository).findById(LOAN_ID);

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      loanApplicationCommentService.deleteByUuid(UUID);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.RESOURCE_EDIT_NOT_ALLOW.getCode());
  }

  @Test
  void givenValidInput_ThenDeleteByUuid_shouldReturnSuccess() {
    LoanApplicationCommentEntity entity = LoanApplicationCommentEntity.builder()
        .uuid(UUID)
        .loanApplicationId(LOAN_ID)
        .status(CommentStatusEnum.DRAFT)
        .build();

    doReturn(Optional.of(entity)).when(loanApplicationCommentRepository).findById(LOAN_ID);

    loanApplicationCommentService.deleteByUuid(UUID);
    verify(loanApplicationCommentRepository, times(1)).deleteById(UUID);
  }

  @Test
  void givenValidInput_ThenHistory_shouldReturnSuccess() {
    try (MockedStatic<AuthorizationUtil> theMock = Mockito.mockStatic(AuthorizationUtil.class)) {
      theMock.when(() -> AuthorizationUtil.getEmail())
          .thenReturn(EMAIL);
      List<LoanApplicationCommentEntity> loanApplicationComments = new ArrayList<>();
      loanApplicationComments.add(LoanApplicationCommentEntity.builder()
          .build());
      doReturn(loanApplicationComments).when(loanApplicationCommentRepository)
          .findByLoanApplicationIdAndCodeOrderByCreatedAt(LOAN_ID, CODE);

      CmsUser cmsUser = CmsUser.builder()
          .fullName("fullName")
          .email("email")
          .build();

      doReturn(cmsUser).when(cmsUserService).findByEmail(EMAIL);

      Cache cache = Mockito.mock(Cache.class);
      Mockito.when(cacheManager.getCache(Mockito.anyString()))
          .thenReturn(cache);

      List<LoanApplicationComment> results = loanApplicationCommentService.history(LOAN_ID, CODE);
      assertEquals(results.size(), 1);
    }
  }

}
