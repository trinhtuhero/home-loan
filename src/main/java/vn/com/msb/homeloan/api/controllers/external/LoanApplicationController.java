package vn.com.msb.homeloan.api.controllers.external;

import com.fasterxml.jackson.core.JsonProcessingException;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CustomerInfoRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.LdpSearchResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationReviewResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CustomerInfoRequest;
import vn.com.msb.homeloan.api.dto.request.LoanApplicationApprovalRequest;
import vn.com.msb.homeloan.api.dto.request.LoanApplicationUpdateStatusRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.LoanApplication;
import vn.com.msb.homeloan.core.model.LoanApplicationReview;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.util.AuthorizationUtil;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/loan-applications")
@RequiredArgsConstructor
public class LoanApplicationController {

  private final LoanApplicationService loanInformationService;

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    LoanApplication loanApplication = loanInformationService.findById(uuid);
    ApiResource apiResource = new ApiResource(
        LoanApplicationResponseMapper.INSTANCE.toDTO(loanApplication), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}/review")
  public ResponseEntity<ApiResource> review(@PathVariable String uuid) {
    LoanApplicationReview loanApplication = loanInformationService.ldpReview(uuid);
    String profileId = AuthorizationUtil.getUserId();
    if (loanApplication != null && loanApplication.getLoanApplication() != null) {
      if (!loanApplication.getLoanApplication().getProfileId().equalsIgnoreCase(profileId)) {
        throw new ApplicationException(ErrorEnum.LOAN_DETAIL_NOT_ALLOW);
      }
    }
    ApiResource apiResource = new ApiResource(
        LoanApplicationReviewResponseMapper.INSTANCE.toDTO(loanApplication), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/customer")
  public ResponseEntity<ApiResource> insertCustomer(
      @Valid @RequestBody CustomerInfoRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    LoanApplication loanApplication = CustomerInfoRequestMapper.INSTANCE.toLoanModel(request);
    ApiResource apiResource = new ApiResource(LoanApplicationResponseMapper.INSTANCE.toDTO(
        loanInformationService.save(loanApplication, ClientTypeEnum.LDP)), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PatchMapping("/customer")
  public ResponseEntity<ApiResource> updateCustomer(
      @Valid @RequestBody CustomerInfoRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    LoanApplication loanApplication = CustomerInfoRequestMapper.INSTANCE.toLoanModel(request);
    ApiResource apiResource = new ApiResource(LoanApplicationResponseMapper.INSTANCE.toDTO(
        loanInformationService.updateCustomer(loanApplication)), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/{loanId}/submit")
  public ResponseEntity<ApiResource> customerSubmitFile(@PathVariable String loanId,
      @Valid @RequestBody LoanApplicationUpdateStatusRequest request) {
    String refEmail = request.getRefEmail();
    if (refEmail != null && !refEmail.isEmpty()) {
      String[] emailStrings = refEmail.split("@");
      refEmail = emailStrings[0] + "@msb";
    }
    loanInformationService.customerSubmitFile(loanId, request.getStatus(), refEmail);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/{loanId}/approval")
  public ResponseEntity<ApiResource> customerApproval(@PathVariable String loanId,
      @Valid @RequestBody LoanApplicationApprovalRequest request) throws JsonProcessingException {
    loanInformationService.customerApprovalChange(loanId, request.getStatus(), request.getNote());
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/list")
  public ResponseEntity<ApiResource> getLoansByProfileId(
      @RequestParam(required = false) String loanCode,
      @RequestParam(required = false) List<String> status,
      @RequestParam(required = false) Long loanAmountFrom,
      @RequestParam(required = false) Long loanAmountTo,
      @RequestParam(required = false) Instant from, @RequestParam(required = false) Instant to) {
    List<LoanApplication> loanApplications = loanInformationService.getLoansByProfileId(loanCode,
        status, loanAmountFrom, loanAmountTo, from, to);
    ApiResource apiResource = new ApiResource(
        LdpSearchResponseMapper.INSTANCE.toResponses(loanApplications), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{uuid}/pre-approval")
  public ResponseEntity<ApiResource> preApproval(@PathVariable String uuid) {
    LoanApplicationReview loanApplication = loanInformationService.review(uuid);
    String profileId = AuthorizationUtil.getUserId();
    if (loanApplication != null && loanApplication.getLoanApplication() != null) {
      if (!loanApplication.getLoanApplication().getProfileId().equalsIgnoreCase(profileId)) {
        throw new ApplicationException(ErrorEnum.LOAN_DETAIL_NOT_ALLOW);
      }
    }
    ApiResource apiResource = new ApiResource(
        LoanApplicationReviewResponseMapper.INSTANCE.toDTO(loanApplication), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
