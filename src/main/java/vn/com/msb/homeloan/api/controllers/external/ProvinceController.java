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
import vn.com.msb.homeloan.api.dto.mapper.ProvinceResponseMapper;
import vn.com.msb.homeloan.core.model.Province;
import vn.com.msb.homeloan.core.service.ProvinceService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/province")
@RequiredArgsConstructor
public class ProvinceController {

  private final ProvinceService provinceService;

  @GetMapping("")
  public ResponseEntity<ApiResource> provinces() {
    List<Province> provinces = provinceService.findAll();
    ApiResource apiResource = new ApiResource(
        ProvinceResponseMapper.INSTANCE.toResponses(provinces), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
