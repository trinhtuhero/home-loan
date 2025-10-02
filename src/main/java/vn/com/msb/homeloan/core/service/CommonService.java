package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.api.dto.request.OTPCommonRequest;
import vn.com.msb.homeloan.api.dto.request.SendOtpRequest;
import vn.com.msb.homeloan.api.dto.response.OTPCommonResponse;

public interface CommonService {

  OTPCommonResponse sendOTP(SendOtpRequest sendOtpRequest);

  OTPCommonResponse confirmOTP(OTPCommonRequest otpCommonRequest);
}
