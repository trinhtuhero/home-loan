package vn.com.msb.homeloan.api.controllers.internal;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
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
import vn.com.msb.homeloan.api.dto.mapper.data.GetLifeInfoDataMapper;
import vn.com.msb.homeloan.api.dto.request.InterestedInInsurance2Request;
import vn.com.msb.homeloan.api.dto.request.insurance.SendLeadToCJ4Request;
import vn.com.msb.homeloan.api.dto.request.insurance.UpdateLeadToCJ4Request;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.model.response.cj4.GetLifeInfo;
import vn.com.msb.homeloan.core.service.InsuranceService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;

@Slf4j
@RestController
@RequestMapping("/api/v1/cms/insurance")
@RequiredArgsConstructor
public class CMSInsuranceController {

  private final InsuranceService insuranceService;

  private final LoanApplicationService loanApplicationService;

  @PostMapping("/send-lead")
  public ResponseEntity<ApiResource> sendLead(@RequestBody @Valid SendLeadToCJ4Request request)
      throws ParseException, IOException {
    insuranceService.sendLead(request.getLoanId(), null);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("update-lead")
  public ResponseEntity<ApiResource> updateLead(@RequestBody @Valid UpdateLeadToCJ4Request request)
      throws ParseException, IOException {
    insuranceService.updateLead(request.getLoanId(), request.getEmplId(),
        InterestedEnum.valueOf(request.getStatus()), "CMS");
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/get-life-info")
  public ResponseEntity<ApiResource> getLifeInfo(@RequestParam(value = "loan_id") String loanId)
      throws ParseException, IOException {

    GetLifeInfo getLifeInfo = insuranceService.getLifeInfo(loanId);

    ApiResource apiResource = new ApiResource(GetLifeInfoDataMapper.INSTANCE.toData(getLifeInfo),
        HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("get-link")
  public ResponseEntity<ApiResource> getLink(@RequestParam(value = "loan_id") String loanId)
      throws ParseException, IOException {
    String link = insuranceService.getLink(loanId);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("link", link);
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/interested-insurance")
  public ResponseEntity<ApiResource> interestedInInsurance(
      @Valid @RequestBody InterestedInInsurance2Request request) {
    loanApplicationService.interestedInInsurance2(request.getLoanId(),
        InterestedEnum.valueOf(request.getStatus()));
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
