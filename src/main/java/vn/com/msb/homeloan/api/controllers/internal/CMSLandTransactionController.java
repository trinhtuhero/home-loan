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
import vn.com.msb.homeloan.core.service.LandTransactionService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/land-transaction")
@RequiredArgsConstructor
public class CMSLandTransactionController {

  private final LandTransactionService landTransactionService;

  @DeleteMapping("/{uuid}")
  public ResponseEntity<Object> delete(@PathVariable("uuid") String uuid) {
    landTransactionService.deleteById(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
