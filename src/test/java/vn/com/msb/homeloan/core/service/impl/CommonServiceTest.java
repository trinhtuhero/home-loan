package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.core.client.NotificationFeignClient;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.CommonService;
import vn.com.msb.homeloan.core.service.OTPService;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;
import vn.com.msb.homeloan.infras.configs.properties.OtpConfig;

@ExtendWith(MockitoExtension.class)
class CommonServiceTest {

  @Mock
  OTPService otpService;

  @Mock
  EnvironmentProperties envProperties;

  @Mock
  NotificationFeignClient notificationClient;

  @Mock
  RedisService redisService;

  @Mock
  ProfileRepository profileRepository;

  @Mock
  ObjectMapper objectMapper;

  CommonService commonService;

  @BeforeEach
  void setUp() {
    this.commonService = new CommonServiceImpl(
        otpService,
        envProperties,
        notificationClient,
        redisService,
        profileRepository,
        objectMapper
    );
  }

  @Test
  void givenInValidInput_ThenSendOtp_shouldReturnFail() {
    SendOtpRequest request = SendOtpRequest.builder()
        .phone("123456")
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      commonService.sendOTP(request);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.OTP_PHONE_INVALID.getCode());
  }

  @Test
  void givenValidInput_ThenSendOtp_shouldReturnProfileNotFound() {
    SendOtpRequest request = SendOtpRequest.builder()
        .phone("0973545654")
        .profileUuid("profileId")
        .build();

    doReturn(Optional.empty()).when(profileRepository).findById(request.getProfileUuid());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      commonService.sendOTP(request);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.PROFILE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSendOtp_shouldReturnOtpBlock() {
    SendOtpRequest request = SendOtpRequest.builder()
        .phone("0973545654")
        .profileUuid("profileId")
        .build();

    doReturn(Optional.of(ProfileEntity.builder().build())).when(profileRepository)
        .findById(request.getProfileUuid());
    doReturn(true).when(otpService).isOtpBlock(any());

    OtpConfig.RetryConfig retryConfig = new OtpConfig.RetryConfig();
    retryConfig.setCount(1);
    retryConfig.setBlockTime(30);
    retryConfig.setIsReset(false);
    doReturn(retryConfig).when(otpService).getResendOtpBlockTime(any());
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      commonService.sendOTP(request);
    });
    assertEquals(exception.getCode().intValue(), ErrorEnum.OTP_BLOCKED.getCode());
  }

}