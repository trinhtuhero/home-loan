package vn.com.msb.homeloan.api.controllers.external;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.mapper.CalculateInsuranceRequestMapper;
import vn.com.msb.homeloan.api.dto.mapper.data.CalculateInsuranceDataMapper;
import vn.com.msb.homeloan.api.dto.mapper.data.ProductDataMapper;
import vn.com.msb.homeloan.api.dto.request.insurance.CJ4CalculateInsuranceRequest;
import vn.com.msb.homeloan.api.dto.request.insurance.UpdateLeadToCJ4Request;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.model.response.cj4.CalculateInsurance;
import vn.com.msb.homeloan.core.model.response.cj4.ProductInfo;
import vn.com.msb.homeloan.core.service.InsuranceService;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/insurance")
@RequiredArgsConstructor
public class InsuranceController {

  private final InsuranceService insuranceService;

  @GetMapping("get-link")
  public ResponseEntity<ApiResource> getLink(@RequestParam(value = "loan_id") String loanId)
      throws ParseException, IOException {
    String link = insuranceService.getLink(loanId);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("link", link);
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("update-lead")
  public ResponseEntity<ApiResource> updateLead(@RequestBody @Valid UpdateLeadToCJ4Request request)
      throws ParseException, IOException {
    insuranceService.updateLead(request.getLoanId(), request.getEmplId(),
        InterestedEnum.valueOf(request.getStatus()), "LDP");
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/products")
  public ResponseEntity<ApiResource> getProducts(@RequestParam(value = "loan_id") String loanId)
      throws ParseException, IOException {
    List<ProductInfo> products = insuranceService.getProducts(loanId);
    ApiResource apiResource = new ApiResource(ProductDataMapper.INSTANCE.toDatas(products),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/calculate-insurance")
  public ResponseEntity<ApiResource> calculateInsurance(
      @RequestBody @Valid CJ4CalculateInsuranceRequest request) throws ParseException, IOException {

    CalculateInsurance calculateInsurance = insuranceService.calculateInsurance(
        CalculateInsuranceRequestMapper.INSTANCE.toModel(request), request.getLoanId());

    ApiResource apiResource = new ApiResource(
        CalculateInsuranceDataMapper.INSTANCE.toData(calculateInsurance), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

}
