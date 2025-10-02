package vn.com.msb.homeloan.api.controllers.internal;

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
@RequestMapping("/api/v1/cms/district")
@RequiredArgsConstructor
public class CMSDistrictController {

  private final DistrictService districtService;

  @GetMapping("/province/{provinceCode}")
  //@PreAuthorize("@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_DISTRICT_FIND_BY_PROVINCE + "')")
  public ResponseEntity<ApiResource> findByProvince(@PathVariable String provinceCode) {
    List<District> districts = districtService.findByProvinceCode(provinceCode);
    ApiResource apiResource = new ApiResource(
        DistrictResponseMapper.INSTANCE.toResponses(districts), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/mvalue/province/{provinceCode}")
  //@PreAuthorize("@customPreAuthorizer.hasPermission('" + PermissionConstants.CJ5_DISTRICT_FIND_BY_PROVINCE + "')")
  public ResponseEntity<ApiResource> findByMvalueProvince(@PathVariable String provinceCode) {
    List<District> districts = districtService.findByProvinceCodeMValue(provinceCode);
    ApiResource apiResource = new ApiResource(
      DistrictResponseMapper.INSTANCE.toResponses(districts), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
