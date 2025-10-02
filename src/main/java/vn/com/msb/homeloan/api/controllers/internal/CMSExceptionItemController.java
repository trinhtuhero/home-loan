package vn.com.msb.homeloan.api.controllers.internal;

import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.ExceptionItemRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.ExceptionItemResponseMapper;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.ExceptionItemRequest;
import vn.com.msb.homeloan.core.model.ExceptionItem;
import vn.com.msb.homeloan.core.service.ExceptionItemService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/exception-item")
@RequiredArgsConstructor
public class CMSExceptionItemController {

  private final ExceptionItemService exceptionItemService;

  @PostMapping
  public ResponseEntity<ApiResource> save(@Valid @RequestBody ExceptionItemRequest request) {
    ExceptionItem exceptionItem = exceptionItemService.save(
        ExceptionItemRequestMapper.INSTANCE.toModel(request));
    ApiResource apiResource = new ApiResource(
        ExceptionItemResponseMapper.INSTANCE.toResponse(exceptionItem), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping(value = "/{loanId}")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) {
    List<ExceptionItem> exceptionItems = exceptionItemService.getByLoanId(loanId);
    ApiResource apiResource = new ApiResource(
        ExceptionItemResponseMapper.INSTANCE.toResponses(exceptionItems), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    exceptionItemService.deleteByUuid(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
