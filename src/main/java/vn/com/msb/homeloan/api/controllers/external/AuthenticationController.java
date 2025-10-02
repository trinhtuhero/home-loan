package vn.com.msb.homeloan.api.controllers.external;

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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.LoginProfileInfoResponse;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.core.jwt.JwtTokenProvider;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;
import vn.com.msb.homeloan.core.service.AuthenticationService;
import vn.com.msb.homeloan.core.service.LoanApplicationService;
import vn.com.msb.homeloan.core.service.LoanUploadFileService;
import vn.com.msb.homeloan.core.util.JWTUtil;

@Slf4j
@RestController
@RequestMapping("/api/v1/ldp/authentication")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService authenticationService;

  private final LoanApplicationService loanApplicationService;

  private final LoanUploadFileService loanUploadFileService;

  private final JwtTokenProvider jwtTokenProvider;

  @PostMapping("/user/login")
  public ResponseEntity<ApiResource> login(@RequestParam String phoneNumber) {
    LoginProfileInfo loginProfileInfo = authenticationService.login(phoneNumber);
    LoginProfileInfoResponse response = LoginProfileInfoResponse.builder()
        .uuid(loginProfileInfo.getUuid())
        .otpUuid(loginProfileInfo.getOtpUuid())
        .build();
    ApiResource apiResource = new ApiResource(response, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/otp/send")
  public ResponseEntity<ApiResource> sendOTP(@Valid @RequestBody SendOtpRequest request) {
    OTPCommonResponse otpCommonResponse = authenticationService.sendOTP(request);
    ApiResource apiResource = new ApiResource(otpCommonResponse.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @PostMapping("/otp/confirm")
  public ResponseEntity<ApiResource> confirmOTP(@Valid @RequestBody OTPCommonRequest request) {
    OTPCommonResponse otpCommonResponse = authenticationService.confirmOTP(request);
    ApiResource apiResource = new ApiResource(otpCommonResponse.getData(), HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }

  @GetMapping("/logout")
  public ResponseEntity logout(@RequestHeader String authorization) {
    String jwtStr = JWTUtil.getJwtStr(authorization);
    authenticationService.logout(jwtStr);
    Map<String, String> dataMap = new HashMap<>();
    dataMap.put("message", "Success");
    ApiResource apiResource = new ApiResource(dataMap, HttpStatus.OK.value());
    return ResponseEntity.ok(apiResource);
  }
}
