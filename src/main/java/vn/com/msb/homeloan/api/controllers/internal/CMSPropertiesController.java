package vn.com.msb.homeloan.api.controllers.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.core.service.PropertyService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/property")
@RequiredArgsConstructor
public class CMSPropertiesController {

  private final PropertyService propertyService;

  @PostMapping("/{name}")
  public ResponseEntity<Object> saveOrUpdateProperties(@PathVariable("name") String name,
      @RequestBody String currentValue) {

    String currentValueResponse = propertyService.saveOrUpdate(name, currentValue);

    return ResponseEntity.ok(currentValueResponse);
  }
}
