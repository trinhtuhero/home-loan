package vn.com.msb.homeloan.api.controllers.external;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationItemRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationItemResponseMapper;
import vn.com.msb.homeloan.api.dto.request.LoanApplicationItemRequest;
import vn.com.msb.homeloan.api.dto.response.LoanApplicationItemResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/loan-application-item")
@RequiredArgsConstructor
@Validated
public class LoanApplicationItemController {

  private final LoanApplicationItemService loanApplicationItemService;

  // list
  @GetMapping(value = "/{loanId}/list")
  public ResponseEntity<ApiResource> list(@PathVariable String loanId) {
    List<LoanApplicationItem> list = loanApplicationItemService.getItemCollateralDistribute(loanId);
    List<LoanApplicationItemResponse> response = LoanApplicationItemResponseMapper.INSTANCE.toLdpResponse(
        list);
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // get
  @GetMapping(value = "/{uuid}")
  public ResponseEntity<ApiResource> get(@PathVariable String uuid) {
    LoanApplicationItem loanApplicationItem = loanApplicationItemService.findById(uuid);
    LoanApplicationItemResponse response = LoanApplicationItemResponseMapper.INSTANCE.toLdpResponse(
        loanApplicationItem);
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // update
  @PutMapping(value = "/{uuid}")
  public ResponseEntity<ApiResource> put(@PathVariable String uuid,
      @RequestBody LoanApplicationItemRequest request) {
    request.setUuid(uuid);
    LoanApplicationItem loanApplicationItem = loanApplicationItemService.save(
        LoanApplicationItemRequestMapper.INSTANCE.toLdpModel(request),0, ClientTypeEnum.LDP);
    LoanApplicationItemResponse response = LoanApplicationItemResponseMapper.INSTANCE.toLdpResponse(
        loanApplicationItem);
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // add
  @PostMapping
  public ResponseEntity<ApiResource> post(@RequestBody LoanApplicationItemRequest request) {
    request.setUuid(null);
    LoanApplicationItem loanApplicationItem = loanApplicationItemService.save(
        LoanApplicationItemRequestMapper.INSTANCE.toLdpModel(request),0, ClientTypeEnum.LDP);
    LoanApplicationItemResponse response = LoanApplicationItemResponseMapper.INSTANCE.toLdpResponse(
        loanApplicationItem);
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // Xóa
  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> delete(@PathVariable String uuid) {
    loanApplicationItemService.delete(uuid, ClientTypeEnum.LDP);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
