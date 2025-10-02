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
import vn.com.msb.homeloan.api.dto.mapper.DistrictResponseMapper;
import vn.com.msb.homeloan.core.model.District;
import vn.com.msb.homeloan.core.service.DistrictService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/district")
@RequiredArgsConstructor
public class DistrictController {

  private final DistrictService districtService;

  @GetMapping("/province/{provinceCode}")
  public ResponseEntity<ApiResource> findByProvince(@PathVariable String provinceCode) {
    List<District> districts = districtService.findByProvinceCode(provinceCode);
    ApiResource apiResource = new ApiResource(
        DistrictResponseMapper.INSTANCE.toResponses(districts), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
