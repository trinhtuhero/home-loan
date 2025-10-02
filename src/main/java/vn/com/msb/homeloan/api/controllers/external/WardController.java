package vn.com.msb.homeloan.api.controllers.external;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.WardResponseMapper;
import vn.com.msb.homeloan.core.model.Ward;
import vn.com.msb.homeloan.core.service.WardService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/ward")
@RequiredArgsConstructor
public class WardController {

  private final WardService wardService;

  @GetMapping("/province/{provinceCode}/district/{districtCode}")
  public ResponseEntity<ApiResource> findByDistrictCode(@PathVariable String provinceCode,
      @PathVariable String districtCode) {
    List<Ward> wards = wardService.findByDistrictCode(provinceCode, districtCode);
    ApiResource apiResource = new ApiResource(WardResponseMapper.INSTANCE.toResponses(wards),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
