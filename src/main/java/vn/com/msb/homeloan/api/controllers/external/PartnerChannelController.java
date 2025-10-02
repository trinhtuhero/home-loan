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
import vn.com.msb.homeloan.api.dto.mapper.PartnerChannelRequestMapper;
import vn.com.msb.homeloan.api.dto.request.PartnerChannelRequest;
import vn.com.msb.homeloan.core.model.PartnerChannel;
import vn.com.msb.homeloan.core.service.PartnerChannelService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/partner-channel")
@RequiredArgsConstructor
public class PartnerChannelController {

  private final PartnerChannelService partnerChannelService;

  @PostMapping("")
  public ResponseEntity<ApiResource> save(@Valid @RequestBody PartnerChannelRequest request) {
    PartnerChannel partnerChannel = partnerChannelService.save(
        PartnerChannelRequestMapper.INSTANCE.toModel(request));
    ApiResource apiResource = new ApiResource(partnerChannel, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
