package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.OverdraftRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.OverdraftResponseMapper;
import vn.com.msb.homeloan.api.dto.request.overdraft.OverdraftRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.service.OverdraftService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/overdraft")
@RequiredArgsConstructor
public class OverdraftController {

  private final OverdraftService overdraftService;

  @PostMapping
  public ResponseEntity<ApiResource> save(@Valid @RequestBody OverdraftRequest overdraftRequest) {
    Overdraft overdraft = overdraftService.save(
        OverdraftRequestMapper.INSTANCE.toModel(overdraftRequest), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        OverdraftResponseMapper.INSTANCE.toLdpResponse(overdraft), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(@Valid @RequestBody OverdraftRequest overdraftRequest) {
    Overdraft overdraft = overdraftService.save(
        OverdraftRequestMapper.INSTANCE.toModel(overdraftRequest), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(
        OverdraftResponseMapper.INSTANCE.toLdpResponse(overdraft), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> delete(@PathVariable String uuid) {
    overdraftService.delete(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
