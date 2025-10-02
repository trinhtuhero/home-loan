package vn.com.msb.homeloan.api.controllers.internal;

import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.OrganizationResponseMapper;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.Organization;
import vn.com.msb.homeloan.core.service.OrganizationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/organization")
@RequiredArgsConstructor
public class CMSOrganizationController {

  private final OrganizationService organizationService;

  @GetMapping("/by-type")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_ORGANIZATION_GET_BY_TYPE
          + "')")
  public ResponseEntity<ApiResource> getByType(@RequestParam String type) {
    List<Organization> organizations = organizationService.getByType(type);
    ApiResource apiResource = new ApiResource(
        OrganizationResponseMapper.INSTANCE.toDTOs(organizations), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/by-area")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_ORGANIZATION_GET_BY_AREA
          + "')")
  public ResponseEntity<ApiResource> getByArea(@RequestParam String area) {
    List<Organization> organizations = organizationService.getDVKD(area, new ArrayList<>());
    ApiResource apiResource = new ApiResource(
        OrganizationResponseMapper.INSTANCE.toDTOs(organizations), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}

