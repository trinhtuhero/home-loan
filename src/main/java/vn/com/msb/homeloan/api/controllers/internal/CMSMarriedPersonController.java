package vn.com.msb.homeloan.api.controllers.internal;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSMarriedPersonRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSMarriedPersonResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSMarriedPersonRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;
import vn.com.msb.homeloan.core.service.MarriedPersonService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/married-person")
@RequiredArgsConstructor
public class CMSMarriedPersonController {

  private final MarriedPersonService marriedPersonService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_MARRIED_PERSON_SAVE + "')")
  public ResponseEntity<ApiResource> rmUpdate(@Valid @RequestBody CMSMarriedPersonRequest request) {
    MarriedPersonEntity marriedPerson = marriedPersonService.save(
        CMSMarriedPersonRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.CMS);
    ApiResource apiResource = new ApiResource(
        CMSMarriedPersonResponseMapper.INSTANCE.toDto(marriedPerson), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
