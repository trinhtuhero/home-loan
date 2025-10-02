package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.LoginProfileInfo;

public interface AuthenticationService {

  LoginProfileInfo login(String phoneNumber);

  OTPCommonResponse sendOTP(SendOtpRequest sendOtpRequest);

  OTPCommonResponse confirmOTP(OTPCommonRequest otpCommonRequest);

  String generateToken(ProfileEntity userInfo);

  boolean logout(String accessToken);
}
