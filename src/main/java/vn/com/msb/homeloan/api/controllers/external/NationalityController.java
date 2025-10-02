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
import vn.com.msb.homeloan.api.dto.mapper.NationalityResponseMapper;
import vn.com.msb.homeloan.core.model.Nationality;
import vn.com.msb.homeloan.core.service.NationalityService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/nationality")
@RequiredArgsConstructor
public class NationalityController {

  private final NationalityService nationalityService;

  @GetMapping("")
  public ResponseEntity<ApiResource> nationalities() {
    List<Nationality> nationalities = nationalityService.findAll();
    ApiResource apiResource = new ApiResource(
        NationalityResponseMapper.INSTANCE.toResponses(nationalities), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
