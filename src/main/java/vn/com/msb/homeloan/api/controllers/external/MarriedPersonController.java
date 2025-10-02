package vn.com.msb.homeloan.api.controllers.external;

import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.MarriedPersonRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.MarriedPersonResponseMapper;
import vn.com.msb.homeloan.api.dto.request.MarriedPersonRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.MarriedPerson;
import vn.com.msb.homeloan.core.model.mapper.MarriedPersonInfoMapper;
import vn.com.msb.homeloan.core.service.MarriedPersonService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/married-person")
@RequiredArgsConstructor
public class MarriedPersonController {

  private final MarriedPersonService marriedPersonService;

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    MarriedPerson marriedPerson = marriedPersonService.findById(uuid);
    ApiResource apiResource = new ApiResource(
        MarriedPersonResponseMapper.INSTANCE.toDto(marriedPerson), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping
  public ResponseEntity<ApiResource> insert(@Valid @RequestBody MarriedPersonRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    MarriedPerson marriedPerson = MarriedPersonInfoMapper.INSTANCE.toModel(
        marriedPersonService.save(MarriedPersonRequestMapper.INSTANCE.toModel(request),
            ClientTypeEnum.LDP));
    ApiResource apiResource = new ApiResource(
        MarriedPersonResponseMapper.INSTANCE.toDto(marriedPerson), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(@Valid @RequestBody MarriedPersonRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    MarriedPerson marriedPerson = MarriedPersonInfoMapper.INSTANCE.toModel(
        marriedPersonService.save(MarriedPersonRequestMapper.INSTANCE.toModel(request),
            ClientTypeEnum.LDP));
    ApiResource apiResource = new ApiResource(
        MarriedPersonResponseMapper.INSTANCE.toDto(marriedPerson), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    marriedPersonService.deleteById(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
