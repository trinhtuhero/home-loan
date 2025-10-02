package vn.com.msb.homeloan.api.controllers.external;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.entity.PlaceOfIssueIdCardEntity;
import vn.com.msb.homeloan.core.service.PlaceOfIssueIdCardService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/id-card")
@RequiredArgsConstructor
public class PlaceOfIssueIdCardController {

  private final PlaceOfIssueIdCardService placeOfIssueIdCardService;

  @GetMapping("/places")
  public ResponseEntity<ApiResource> getAll() {
    List<PlaceOfIssueIdCardEntity> places = placeOfIssueIdCardService.getAll();
    ApiResource apiResource = new ApiResource(places, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
