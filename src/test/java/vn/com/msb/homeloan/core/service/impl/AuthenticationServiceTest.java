package vn.com.msb.homeloan.core.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.DataCommonResponse;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.jwt.JwtTokenProvider;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.AuthenticationService;
import vn.com.msb.homeloan.core.service.CommonService;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.core.service.mockdata.BuildData;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

  AuthenticationService authenticationService;

  @Mock
  ProfileRepository profileRepository;

  @Mock
  RedisService redisService;

  @Mock
  CommonService commonService;

  @Mock
  JwtTokenProvider jwtTokenProvider;

  @BeforeEach
  void setUp() {
    this.authenticationService = new AuthenticationServiceImpl(
        profileRepository, redisService, commonService, jwtTokenProvider);
  }

  @Test
  void givenValidInput_ThenLogin_shouldReturnPhoneInvalid() {
    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      authenticationService.login("123456");
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.OTP_PHONE_INVALID.getCode());
  }

  @Test
  void givenProfileExisted_ThenLogin_shouldReturnSuccess() {
    ProfileEntity profile = BuildData.buildProfile("ProfileId");
    doReturn(Optional.of(profile)).when(profileRepository).findOneByPhone(profile.getPhone());

    DataCommonResponse data = new DataCommonResponse();
    data.setUuid("OTP_UUID");
    OTPCommonResponse otpCommonResponse = new OTPCommonResponse();
    otpCommonResponse.setData(data);
    doReturn(otpCommonResponse).when(commonService).sendOTP(any());

    LoginProfileInfo loginProfileInfo = authenticationService.login(profile.getPhone());

    assertEquals(loginProfileInfo.getOtpUuid(), data.getUuid());
  }

  @Test
  void givenProfileNotFound_ThenLogin_shouldReturnSuccess() {
    ProfileEntity profile = BuildData.buildProfile("ProfileId");
    doReturn(Optional.empty()).when(profileRepository).findOneByPhone(profile.getPhone());

    doReturn(profile).when(profileRepository).save(any());

    DataCommonResponse data = new DataCommonResponse();
    data.setUuid("OTP_UUID");
    OTPCommonResponse otpCommonResponse = new OTPCommonResponse();
    otpCommonResponse.setData(data);
    doReturn(otpCommonResponse).when(commonService).sendOTP(any());

    LoginProfileInfo loginProfileInfo = authenticationService.login(profile.getPhone());

    assertEquals(loginProfileInfo.getOtpUuid(), data.getUuid());
  }

  @Test
  void givenValidInput_ThenSendOtp_shouldReturnProfileNotFound() {
    doReturn(Optional.empty()).when(profileRepository).findById("PROFILE_ID");

    SendOtpRequest sendOtpRequest = SendOtpRequest.builder()
        .phone("123456789")
        .profileUuid("PROFILE_ID")
        .build();

    ApplicationException exception = assertThrows(ApplicationException.class, () -> {
      authenticationService.sendOTP(sendOtpRequest);
    });

    assertEquals(exception.getCode().intValue(), ErrorEnum.PROFILE_NOT_FOUND.getCode());
  }

  @Test
  void givenValidInput_ThenSendOtp_shouldReturnSuccess() {
    ProfileEntity profile = BuildData.buildProfile("ProfileId");
    doReturn(Optional.of(profile)).when(profileRepository).findById(profile.getUuid());

    SendOtpRequest sendOtpRequest = SendOtpRequest.builder()
        .phone("123456789")
        .profileUuid(profile.getUuid())
        .build();

    DataCommonResponse data = new DataCommonResponse();
    data.setUuid("OTP_UUID");
    OTPCommonResponse otpCommonResponse = new OTPCommonResponse();
    otpCommonResponse.setData(data);
    doReturn(otpCommonResponse).when(commonService).sendOTP(any());

    OTPCommonResponse result = authenticationService.sendOTP(sendOtpRequest);
    assertEquals(result.getData().getUuid(), data.getUuid());
  }

  @Test
  void givenValidInput_ThenConfirmOtp_shouldReturnSuccess() {
    OTPCommonRequest otpCommonRequest = new OTPCommonRequest();
    otpCommonRequest.setOtp("111111");
    otpCommonRequest.setUuid("uuid");
    OTPCommonResponse otpCommonResponse = new OTPCommonResponse();
    DataCommonResponse data = new DataCommonResponse();
    data.setUuid("OTP_UUID");
    otpCommonResponse.setData(data);

    doReturn(otpCommonResponse).when(commonService).confirmOTP(any());

    ProfileEntity profile = BuildData.buildProfile("ProfileId");
    doReturn(profile).when(redisService).get(any());

    doReturn("token").when(jwtTokenProvider).generateToken(any());

    OTPCommonResponse result = authenticationService.confirmOTP(otpCommonRequest);
    assertEquals(result.getData().getToken(), "token");
  }

  @Test
  void givenValidInput_ThenLogout_shouldReturnSuccess() {
    Boolean result = authenticationService.logout("token");
    assertTrue(result);
  }
}