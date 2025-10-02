package vn.com.msb.homeloan.api.controllers.external;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.MtkTrackingRequestMapper;
import vn.com.msb.homeloan.api.dto.request.MtkTrackingRequest;
import vn.com.msb.homeloan.core.model.MtkTracking;
import vn.com.msb.homeloan.core.service.MtkTrackingService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/mtk-tracking")
@RequiredArgsConstructor
public class MtkTrackingController {

  private final MtkTrackingService mtkTrackingService;

  @PostMapping
  public ResponseEntity<ApiResource> insert(@Valid @RequestBody MtkTrackingRequest request) {
    MtkTracking mtkTracking = mtkTrackingService.save(
        MtkTrackingRequestMapper.INSTANCE.toModel(request));
    ApiResource apiResource = new ApiResource(mtkTracking, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
