package vn.com.msb.homeloan.api.controllers.internal;

import java.sql.SQLException;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CreditAppraisalRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CreditAppraisalResponseMapper;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.CreditAppraisalRequest;
import vn.com.msb.homeloan.api.dto.response.creditAppraisal.CreditRatioResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CreditAppraisal;
import vn.com.msb.homeloan.core.service.CreditAppraisalService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/credit-appraisal")
@RequiredArgsConstructor
public class CMSCreditAppraisalController {

  private final CreditAppraisalService creditAppraisalService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CREDIT_APPRAISAL_SAVE + "')")
  public ResponseEntity<ApiResource> onSave(@Valid @RequestBody CreditAppraisalRequest request) {
    ApiResource apiResource = new ApiResource(CreditAppraisalResponseMapper.INSTANCE.toDTO(
        creditAppraisalService.save(CreditAppraisalRequestMapper.INSTANCE.toModel(request))),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  @PreAuthorize("@customPreAuthorizer.hasPermission('"
      + PermissionConstants.CJ5_CREDIT_APPRAISAL_GET_BY_LOANID + "')")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) throws SQLException {
    ApiResource apiResource = new ApiResource(creditAppraisalService.getByLoanId(loanId),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // các chỉ tín dụng
  @GetMapping("/{loanId}/credit-ratio")
  public ResponseEntity<ApiResource> creditRatios(@PathVariable String loanId) throws SQLException {
    CreditAppraisal creditAppraisal = creditAppraisalService.getByLoanId(loanId);
    CreditRatioResponse creditRatioResponse = CreditAppraisalResponseMapper.INSTANCE.toCreditRatioResponse(
        creditAppraisal);
    ApiResource apiResource = new ApiResource(creditRatioResponse, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
