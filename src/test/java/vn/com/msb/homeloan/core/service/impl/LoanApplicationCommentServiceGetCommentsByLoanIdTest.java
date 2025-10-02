package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.core.constant.CommentStatusEnum;
import vn.com.msb.homeloan.core.entity.LoanApplicationCommentEntity;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;
import vn.com.msb.homeloan.core.repository.LoanApplicationCommentRepository;
import vn.com.msb.homeloan.core.service.CmsUserService;
import vn.com.msb.homeloan.core.service.LoanApplicationCommentService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

@ExtendWith(MockitoExtension.class)
class LoanApplicationCommentServiceGetCommentsByLoanIdTest {

  LoanApplicationCommentService loanApplicationCommentService;

  @Mock
  LoanApplicationCommentRepository loanApplicationCommentRepository;

  @Mock
  CmsUserService cmsUserService;

  @Mock
  LoanApplicationService loanApplicationService;

  @BeforeEach
  void setUp() {
    this.loanApplicationCommentService = new LoanApplicationCommentServiceImpl(
        loanApplicationCommentRepository,
        loanApplicationService,
        cmsUserService,
        null,
        null,
        null,
        null
    );
  }

  @Test
  @DisplayName("LoanApplicationService Test getCommentsByLoanId Success")
  void givenValidInput_ThenGetCommentsByLoanId_shouldReturnSuccess()
      throws JsonProcessingException {
    String LOAN_ID = "6cb236d0-160d-4b2a-abd4-4b89eac4c9bd";
    String uuid = "6cb236d0-160d-4b2a-abd4-4b89eac4c9ee";
    LoanApplicationCommentEntity loanApplicationCommentEntity = LoanApplicationCommentEntity.builder()
        .loanApplicationId(LOAN_ID)
        .comment("comment")
        .status(CommentStatusEnum.NEW)
        .uuid(uuid).build();
    List<LoanApplicationCommentEntity> lstLoanApplicationCommentEntityInDB = new ArrayList<>();
    lstLoanApplicationCommentEntityInDB.add(loanApplicationCommentEntity);

    doReturn(lstLoanApplicationCommentEntityInDB).when(loanApplicationCommentRepository)
        .findByLoanApplicationIdAndStatusOrderByCreatedAt(LOAN_ID,
            CommentStatusEnum.NEW.toString());
    List<LoanApplicationComment> lstResult = loanApplicationCommentService.getCommentsByLoanId(
        LOAN_ID);
    assertTrue(uuid.equalsIgnoreCase(lstResult.get(0).getUuid()), String.valueOf(true));
  }
}