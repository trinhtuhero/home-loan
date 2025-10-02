package vn.com.msb.homeloan.api.controllers.internal;

import java.sql.SQLException;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CreditworthinessItemRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.CreditworthinessItemResponseMapper;
import vn.com.msb.homeloan.api.dto.request.creditappraisal.CreditworthinessItemRequest;
import vn.com.msb.homeloan.core.service.CreditworthinessItemService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/creditworthiness-item")
@RequiredArgsConstructor
public class CMSCreditworthinessItemController {

  private final CreditworthinessItemService creditworthinessItemService;

  @PostMapping
  public ResponseEntity<ApiResource> save(@Valid @RequestBody CreditworthinessItemRequest request) {
    ApiResource apiResource = new ApiResource(
        CreditworthinessItemResponseMapper.INSTANCE.toDTO(creditworthinessItemService.save(
            CreditworthinessItemRequestMapper.INSTANCE.toModel(request))),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/{loanId}")
  public ResponseEntity<ApiResource> getByLoanId(@PathVariable String loanId) throws SQLException {
    ApiResource apiResource = new ApiResource(
        CreditworthinessItemResponseMapper.INSTANCE.toDTOs(
            creditworthinessItemService.getByLoanId(loanId)),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @DeleteMapping("/{uuid}")
  public ResponseEntity<ApiResource> deleteById(@PathVariable String uuid) {
    creditworthinessItemService.deleteByUuid(uuid);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
