package vn.com.msb.homeloan.api.controllers.internal;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CreditInstitutionResponseMapper;
import vn.com.msb.homeloan.api.dto.response.CreditInstitutionResponse;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.service.CreditInstitutionService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/credit-institution")
@RequiredArgsConstructor
public class CreditInstitutionController {

  private final CreditInstitutionService creditInstitutionService;

  @GetMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CREDIT_INSTITUTION_FIND_ALL
          + "')")
  public ResponseEntity<ApiResource> getAll() {
    List<CreditInstitutionResponse> data = CreditInstitutionResponseMapper.INSTANCE.toDtos(
        creditInstitutionService.getAll());
    ApiResource apiResource = new ApiResource(data, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
