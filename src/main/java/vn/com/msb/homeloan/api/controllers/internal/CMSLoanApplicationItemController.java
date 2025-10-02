package vn.com.msb.homeloan.api.controllers.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanItemAndOverdraftMapper;
import vn.com.msb.homeloan.api.dto.mapper.CMSLoanItemAndOverdraftResponseMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationItemRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.LoanApplicationItemResponseMapper;
import vn.com.msb.homeloan.api.dto.request.CMSLoanApplicationItemRequest;
import vn.com.msb.homeloan.api.dto.request.overdraft.CMSLoanItemAndOverdraftRequest;
import vn.com.msb.homeloan.api.dto.response.CMSLoanApplicationItemResponse;
import vn.com.msb.homeloan.api.dto.response.overdraft.CMSLoanItemAndOverdraftResponse;
import vn.com.msb.homeloan.core.constant.ClientTypeEnum;
import vn.com.msb.homeloan.core.model.LoanApplicationItem;
import vn.com.msb.homeloan.core.model.LoanItemAndOverdraft;
import vn.com.msb.homeloan.core.model.overdraft.Overdraft;
import vn.com.msb.homeloan.core.service.LoanApplicationItemService;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@Validated
public class CMSLoanApplicationItemController {

  private final LoanApplicationItemService loanApplicationItemService;

  //list
  @GetMapping(value = "/api/v1/cms/loan-application-item/{loanId}/list")
  public ResponseEntity<ApiResource> list(@PathVariable String loanId) {
    List<LoanApplicationItem> list = loanApplicationItemService.getItemCollateralDistribute(loanId);
    List<CMSLoanApplicationItemResponse> response = LoanApplicationItemResponseMapper.INSTANCE.toCmsResponse(
        list);
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  //list v2
  @GetMapping(value = "/api/v2/cms/loan-application-item/{loanId}/list")
  public ResponseEntity<ApiResource> listV2(@PathVariable String loanId) {

    List<LoanApplicationItem> loanApplicationItems = loanApplicationItemService.getItemCollateralDistribute(
        loanId);
    List<Overdraft> overdrafts = loanApplicationItemService.getOverdraftCollateralDistribute(
        loanId);

    LoanItemAndOverdraft loanItemAndOverdraft = new LoanItemAndOverdraft(loanApplicationItems,
        overdrafts);

    CMSLoanItemAndOverdraftResponse response = CMSLoanItemAndOverdraftResponseMapper.INSTANCE.toResponse(
        loanItemAndOverdraft);

    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/api/v2/cms/loan-application-item")
  public ResponseEntity<ApiResource> savesV2(
      @Valid @RequestBody CMSLoanItemAndOverdraftRequest request) {
    ApiResource apiResource = new ApiResource(
        CMSLoanItemAndOverdraftResponseMapper.INSTANCE.toResponse(
            loanApplicationItemService.saveLoanItemAndOverdraft(
                CMSLoanItemAndOverdraftMapper.INSTANCE.toModel(request), ClientTypeEnum.CMS)),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // saves
  @PostMapping("/api/v1/cms/loan-application-item")
  public ResponseEntity<ApiResource> saves(
      @Valid @RequestBody List<CMSLoanApplicationItemRequest> list) {
    ApiResource apiResource = new ApiResource(
        LoanApplicationItemResponseMapper.INSTANCE.toCmsResponse(loanApplicationItemService.saves(
            LoanApplicationItemRequestMapper.INSTANCE.toCmsModels(list),0, ClientTypeEnum.CMS)),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  // Xóa
  @DeleteMapping("/api/v1/cms/loan-application-item/{uuid}")
  public ResponseEntity<ApiResource> delete(@PathVariable String uuid) {
    loanApplicationItemService.delete(uuid, ClientTypeEnum.CMS);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
