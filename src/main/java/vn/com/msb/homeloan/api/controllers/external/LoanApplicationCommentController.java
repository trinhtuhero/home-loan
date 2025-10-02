package vn.com.msb.homeloan.api.controllers.external;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.model.LoanApplicationComment;
import vn.com.msb.homeloan.core.service.LoanApplicationCommentService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/comment")
@RequiredArgsConstructor
public class LoanApplicationCommentController {

  private final LoanApplicationCommentService loanApplicationCommentService;

  @GetMapping("/loan-application/{loanId}")
  public ResponseEntity<ApiResource> comments(@PathVariable String loanId) {
    List<LoanApplicationComment> comments = loanApplicationCommentService.getCommentsByLoanId(
        loanId);
    ApiResource apiResource = new ApiResource(comments, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
