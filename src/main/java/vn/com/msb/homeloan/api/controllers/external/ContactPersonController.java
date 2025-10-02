package vn.com.msb.homeloan.api.controllers.external;

import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.ContactPersonRequestMapper;
import vn.com.msb.homeloan.api.dto.request.ContactPersonRequest;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ContactPerson;
import vn.com.msb.homeloan.core.service.ContactPersonService;
import vn.com.msb.homeloan.core.util.StringUtils;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/contact-person")
@RequiredArgsConstructor
public class ContactPersonController {

  private final ContactPersonService contactPersonService;

  @GetMapping("/{uuid}")
  public ResponseEntity<ApiResource> findById(@PathVariable String uuid) {
    ApiResource apiResource = new ApiResource(contactPersonService.findById(uuid),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping
  public ResponseEntity<ApiResource> insert(@Valid @RequestBody ContactPersonRequest request) {
    if (!StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID);
    }
    ContactPerson contactPerson = contactPersonService.save(
        ContactPersonRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(contactPerson, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PutMapping
  public ResponseEntity<ApiResource> update(@Valid @RequestBody ContactPersonRequest request) {
    if (StringUtils.isEmpty(request.getUuid())) {
      throw new ApplicationException(ErrorEnum.UUID_INVALID, "");
    }
    ContactPerson contactPerson = contactPersonService.save(
        ContactPersonRequestMapper.INSTANCE.toModel(request), ClientTypeEnum.LDP);
    ApiResource apiResource = new ApiResource(contactPerson, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
