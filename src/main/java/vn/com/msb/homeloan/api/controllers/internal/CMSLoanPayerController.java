package vn.com.msb.homeloan.api.controllers.internal;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanPayerRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanPayerResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSLoanPayerRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.LoanPayer;
import vn.com.msb.homeloan.core.service.LoanPayerService;

@Validated
@Slf4j
@RestController
@RequestMapping("/api/v1/cms/loan-payer")
@RequiredArgsConstructor
public class CMSLoanPayerController {

  private final LoanPayerService loanPayerService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_PAYER_SAVE + "')")
  public ResponseEntity<ApiResource> update(@Valid @RequestBody List<CMSLoanPayerRequest> request) {
    List<LoanPayer> loanPayers = loanPayerService.save(
        CMSLoanPayerRequestMapper.INSTANCE.toModels(request));
    ApiResource apiResource = new ApiResource(
        CMSLoanPayerResponseMapper.INSTANCE.modelToResponses(loanPayers), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_LOAN_PAYER_DELETE_BY_ID
          + "')")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    loanPayerService.deleteById(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
