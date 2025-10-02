package vn.com.msb.homeloan.core.service.impl;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.constant.ProfileStatusEnum;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.jwt.JwtTokenProvider;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;
import vn.com.msb.homeloan.core.model.mapper.LoginProfileInfoMapper;
import vn.com.msb.homeloan.core.repository.ProfileRepository;
import vn.com.msb.homeloan.core.service.AuthenticationService;
import vn.com.msb.homeloan.core.service.CommonService;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.core.util.StringUtils;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

  private static final int TTL = 3600;

  private final ProfileRepository profileRepository;
  private final RedisService redisService;
  private final CommonService commonService;
  private final JwtTokenProvider jwtTokenProvider;

  @Override
  public LoginProfileInfo login(String phoneNumber) {
    if (StringUtils.isEmpty(phoneNumber)
        || !StringUtils.isPhone(phoneNumber)) {
      throw new ApplicationException(ErrorEnum.OTP_PHONE_INVALID);
    }

    ProfileEntity profile;
    Optional<ProfileEntity> opProfile = profileRepository.findOneByPhone(phoneNumber);

    if (!opProfile.isPresent()) {
      profile = ProfileEntity.builder()
          .phone(phoneNumber)
          .status(ProfileStatusEnum.ACTIVE)
          .build();
      profile = profileRepository.save(profile);
    } else {
      profile = opProfile.get();
    }

    //send OTP
    SendOtpRequest sendOtpRequest = SendOtpRequest.builder()
        .phone(phoneNumber)
        .profileUuid(profile.getUuid())
        .build();

    OTPCommonResponse otpCommonResponse = commonService.sendOTP(sendOtpRequest);

    LoginProfileInfo loginProfileInfo = LoginProfileInfoMapper.INSTANCE.toModel(profile);
    loginProfileInfo.setOtpUuid(otpCommonResponse.getData().getUuid());

    return loginProfileInfo;
  }

  @Override
  public OTPCommonResponse sendOTP(SendOtpRequest sendOtpRequest) {
    Optional<ProfileEntity> entityOptional = profileRepository.findById(
        sendOtpRequest.getProfileUuid());
    if (!entityOptional.isPresent()) {
      throw new ApplicationException(ErrorEnum.PROFILE_NOT_FOUND, sendOtpRequest.getProfileUuid());
    }

    return commonService.sendOTP(sendOtpRequest);
  }

  @Override
  public OTPCommonResponse confirmOTP(OTPCommonRequest otpCommonRequest) {
    OTPCommonResponse otpCommonResponse = commonService.confirmOTP(otpCommonRequest);
    ProfileEntity profile = redisService.get(
        Constants.PREFIX_AUTHENTICATION + otpCommonRequest.getUuid());
    otpCommonResponse.getData().setToken(generateToken(profile));
    return otpCommonResponse;
  }

  @Override
  public String generateToken(ProfileEntity profile) {
    return jwtTokenProvider.generateToken(profile);
  }

  @Override
  public boolean logout(String accessToken) {
    String key = Constants.PREFIX_BLACKLIST + accessToken;
    redisService.put(key, null, TTL, TimeUnit.SECONDS);
    return true;
  }
}
