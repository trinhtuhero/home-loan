package vn.com.msb.homeloan.api.controllers.internal;

import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.service.OverdraftService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/overdraft")
@RequiredArgsConstructor
public class CMSOverdraftController {

  private final OverdraftService overdraftService;

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Object> delete(@PathVariable String uuid) {
    overdraftService.delete(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

}
