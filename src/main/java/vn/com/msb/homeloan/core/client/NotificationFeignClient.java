package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendMailRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendSMSRequest;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;
import vn.com.msb.homeloan.api.dto.response.DataCommonResponse;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(name = "notify-common-feign-client", url = "${feign.common.notify.url}", configuration = {
    EncoderFeignConfig.class})
public interface NotificationFeignClient {

  @PostMapping(value = "/api/v1/otp/send")
  ResponseEntity<OTPCommonResponse> sendOTP(@RequestHeader("client_code") String clientCode,
      @RequestBody SendOtpCommonRequest sendOtpRequest);

  @PostMapping(value = "/api/v1/otp/verify")
  ResponseEntity<OTPCommonResponse> confirmOTP(@RequestBody OTPCommonRequest otpCommonRequest);

  @PostMapping(value = "/api/v1/messaging/email/send", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ApiInternalResponse<DataCommonResponse> sendMail(@RequestHeader("client_code") String clientCode,
      @RequestBody SendMailRequest sendMailRequest);

  @PostMapping(value = "/api/v1/messaging/sms/send")
  ApiInternalResponse<DataCommonResponse> sendSMS(
      @RequestHeader(value = "client_code") String clientCode,
      @RequestBody SendSMSRequest sendRequest);

}
