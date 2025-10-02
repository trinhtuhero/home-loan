package vn.com.msb.homeloan.api.controllers.external;

import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.service.PropertyService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/home-page")
@RequiredArgsConstructor
@Validated
public class HomePageController {

  private final PropertyService propertyService;

  @GetMapping(value = "/configuration", produces = {"application/json"})
  public ResponseEntity getConfiguration(
      @RequestParam(required = false) @Pattern(regexp = "^(HOME_PAGE|BAT_DONG_SAN|TIEU_DUNG|XAY_SUA_NHA|HOUSEHOLD)$", message = "must match pattern") String type) {
    String string = propertyService.getHomePageConfiguration(type);
    return ResponseEntity.ok(string);
  }

  @GetMapping(value = "/header-footer", produces = {"application/json"})
  public ResponseEntity getHeaderFooter() {
    String string = propertyService.getByName(Constants.HEADER_FOOTER_CONFIG, String.class);
    return ResponseEntity.ok(string);
  }

}
