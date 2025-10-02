package vn.com.msb.homeloan.core.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.FeignException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.ErrorCommonResponse;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.exception.NotAcceptableException;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.CommonService;
import vn.com.msb.homeloan.core.service.OTPService;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;
import vn.com.msb.homeloan.infras.configs.properties.OtpConfig;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class CommonServiceImpl implements CommonService {

  private final OTPService otpService;
  private final EnvironmentProperties envProperties;
  private final NotificationFeignClient notificationClient;
  private final RedisService redisService;
  private final ProfileRepository profileRepository;
  private final ObjectMapper objectMapper;

  @Override
  public OTPCommonResponse sendOTP(SendOtpRequest sendOtpRequest) {
    if (!StringUtils.isPhone(sendOtpRequest.getPhone())) {
      throw new ApplicationException(ErrorEnum.OTP_PHONE_INVALID);
    }
    try {
      ProfileEntity profile = profileRepository.findById(sendOtpRequest.getProfileUuid())
          .orElseThrow(() -> new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND,
              sendOtpRequest.getProfileUuid()));

      String redisKey = String.format("%s%s", Constants.PREFIX_SEND_OTP_RATE,
          sendOtpRequest.getPhone());
      boolean isBlock = otpService.isOtpBlock(redisKey);
      OtpConfig.RetryConfig retryConfig = otpService.getResendOtpBlockTime(redisKey);
      if (isBlock) {
        otpService.incrementSendOtpCounter(redisKey, retryConfig, false);
        throw new NotAcceptableException(ErrorEnum.OTP_BLOCKED,
            String.valueOf(retryConfig.getBlockTime()));
      }

      SendOtpCommonRequest otpRequest = new SendOtpCommonRequest(
          Collections.singletonList(sendOtpRequest.getPhone()));
      ResponseEntity<OTPCommonResponse> response = notificationClient.sendOTP(
          envProperties.getClientCode(), otpRequest);
      OTPCommonResponse otpCommonResponse = response.getBody();
      if (otpCommonResponse != null) {
        if (otpCommonResponse.getStatus() != null && !otpCommonResponse.getStatus()
            .equals(HttpStatus.OK.value())) {
          throw new ApplicationException(ErrorEnum.OTP_SEND_FAILED);
        }

        otpService.incrementSendOtpCounter(redisKey, retryConfig, true);
        if (otpCommonResponse.getData() != null) {
          redisService.put(otpCommonResponse.getData().getUuid(), sendOtpRequest.getPhone(), 10,
              TimeUnit.MINUTES);
          redisService.put(Constants.PREFIX_AUTHENTICATION + otpCommonResponse.getData().getUuid(),
              ProfileEntity.builder()
                  .phone(profile.getPhone())
                  .uuid(profile.getUuid())
                  .build(),
              5, TimeUnit.MINUTES);
        }
      }

      return otpCommonResponse;
    } catch (FeignException e) {
      log.error("sendOTP:" + e.getMessage());
      throw new ApplicationException(ErrorEnum.OTP_SEND_FAILED);
    }
  }

  @Override
  public OTPCommonResponse confirmOTP(OTPCommonRequest otpCommonRequest) {
    if (otpCommonRequest == null || StringUtils.isEmpty(otpCommonRequest.getUuid())
        || StringUtils.isEmpty(otpCommonRequest.getOtp())) {
      throw new ApplicationException(ErrorEnum.INVALID_FORM);
    }

    String phoneNumber = redisService.get(otpCommonRequest.getUuid());
    if (StringUtils.isEmpty(phoneNumber)) {
      log.error(otpCommonRequest.getUuid() + ": invalid, không tồn tại trong redis");
      throw new ApplicationException(ErrorEnum.OTP_UUID_INVALID);
    }

    try {
      ResponseEntity<OTPCommonResponse> response = notificationClient.confirmOTP(otpCommonRequest);
      redisService.remove(String.format("%s%s", Constants.PREFIX_SEND_OTP_RATE, phoneNumber));
      redisService.remove(otpCommonRequest.getUuid());
      return response.getBody();
    } catch (FeignException e) {
      log.error("confirmOTP:" + e.getMessage());
      checkOtpOverLimit(e.contentUTF8());
      throw new ApplicationException(ErrorEnum.OTP_VERIFY_FAILED);
    }
  }

  private void checkOtpOverLimit(String response) {
    OTPCommonResponse otpCommonResponse;
    try {
      otpCommonResponse = objectMapper.readValue(response, OTPCommonResponse.class);
    } catch (Exception e) {
      throw new ApplicationException(ErrorEnum.OTP_VERIFY_FAILED);
    }

    if (otpCommonResponse != null && !CollectionUtils.isEmpty(otpCommonResponse.getErrors())) {
      ErrorCommonResponse errorCommonResponse = otpCommonResponse.getErrors().get(0);
      if ("-1".equalsIgnoreCase(errorCommonResponse.getCode())) {
        throw new ApplicationException(ErrorEnum.OTP_OVER_LIMIT);
      }

      if ("-2".equalsIgnoreCase(errorCommonResponse.getCode())) {
        throw new ApplicationException(ErrorEnum.OTP_EXPIRED);
      }

    }
  }
}
