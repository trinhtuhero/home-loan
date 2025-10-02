package vn.com.msb.homeloan.api.controllers.internal;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CmsUserSearchRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CreateCmsUserRequestMapper;
import vn.com.msb.homeloan.api.dto.request.CmsUserSearchRequest;
import vn.com.msb.homeloan.api.dto.request.CreateCmsUserRequest;
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.model.CmsUser;
import vn.com.msb.homeloan.core.model.CmsUserSearch;
import vn.com.msb.homeloan.core.service.CmsUserService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/cms-user")
@RequiredArgsConstructor
public class CMSCmsUserController {

  private final CmsUserService cmsUserService;

  @GetMapping()
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CMS_USER_FIND_ALL + "')")
  public ResponseEntity<ApiResource> getAllCmsUser() {
    ApiResource apiResource = new ApiResource(cmsUserService.getAllCmsUser(),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/search")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CMS_USER_SEARCH + "')")
  public ResponseEntity<ApiResource> search(@RequestBody CmsUserSearchRequest request) {

    CmsUserSearch result = cmsUserService.cmsUserSearch(
        CmsUserSearchRequestMapper.INSTANCE.toModel(request));
    ApiResource apiResource = new ApiResource(result, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/import")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CMS_USER_IMPORT + "')")
  public ResponseEntity<?> importCmsUsers(@RequestParam("file") MultipartFile multipartFile)
      throws Exception {
    byte[] data = cmsUserService.importCmsUsers(multipartFile);
    DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
    String currentDateTime = dateFormatter.format(new Date());
    if (data.length == 0) {
      Map<String, String> dataMap = new HashMap<>();
      dataMap.put("message", "Success");
      ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
      return ResponseEntity.ok(apiResource);
    } else {
      return ResponseEntity.badRequest()
          .header(HttpHeaders.CONTENT_DISPOSITION,
              "attachment; filename=Result_" + currentDateTime + ".xlsx")
          .contentType(MediaType.parseMediaType("application/csv"))
          .body(data);
    }
  }

  @PostMapping()
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_CMS_USER_CREATE + "')")
  public ResponseEntity<ApiResource> createCmsUser(
      @Valid @RequestBody CreateCmsUserRequest request) {
    CmsUser result = cmsUserService.createCmsUser(
        CreateCmsUserRequestMapper.INSTANCE.toModel(request));
    ApiResource apiResource = new ApiResource(result, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
