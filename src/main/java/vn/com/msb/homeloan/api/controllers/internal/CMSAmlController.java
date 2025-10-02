package vn.com.msb.homeloan.api.controllers.internal;

import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSAmlResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSGetAmlInfoResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSAmlCheckRequest;
import vn.com.msb.homeloan.core.model.Aml;
import vn.com.msb.homeloan.core.model.CMSGetAmlInfo;
import vn.com.msb.homeloan.core.service.AmlService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/aml")
@RequiredArgsConstructor
public class CMSAmlController {

  private final AmlService amlService;

  @PostMapping("check")
  public ResponseEntity<ApiResource> checkAml(@Valid @RequestBody CMSAmlCheckRequest request) {
    List<Aml> response = amlService.checkAmls(request.getLoanApplicationId(),
        request.getAmlRequests());
    ApiResource apiResource = new ApiResource(
        CMSAmlResponseMapper.INSTANCE.toCmsResponses(response), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  public ResponseEntity<ApiResource> getAmlInfo(@PathVariable String loanId) {
    List<CMSGetAmlInfo> result = amlService.getAmlInfo(loanId);
    ApiResource apiResource = new ApiResource(
        CMSGetAmlInfoResponseMapper.INSTANCE.toCmsResponses(result), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
