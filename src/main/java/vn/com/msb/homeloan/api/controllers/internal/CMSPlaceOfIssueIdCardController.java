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
import vn.com.msb.homeloan.core.constant.PermissionConstants;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.service.PlaceOfIssueIdCardService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/id-card")
@RequiredArgsConstructor
public class CMSPlaceOfIssueIdCardController {

  private final PlaceOfIssueIdCardService placeOfIssueIdCardService;

  @GetMapping("/places")
  @PreAuthorize(
      "@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_ID_CARD_PLACES + "')")
  public ResponseEntity<ApiResource> getAll() {
    List<PlaceOfIssueIdCardEntity> places = placeOfIssueIdCardService.getAll();
    ApiResource apiResource = new ApiResource(places, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
