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
import vn.com.msb.homeloan.api.dto.mapper.CMSContactPersonRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CMSContactPersonRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/contact-person")
@RequiredArgsConstructor
public class CMSContactPersonController {

  private final ContactPersonService contactPersonService;

  @PutMapping
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CONTACT_PERSON_UPDATE + "')")
  public ResponseEntity<ApiResource> update(@Valid @RequestBody CMSContactPersonRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID, "");
    }
    ContactPerson contactPerson = contactPersonService.save(
        CMSContactPersonRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.CMS);
    ApiResource apiResource = new ApiResource(contactPerson, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
