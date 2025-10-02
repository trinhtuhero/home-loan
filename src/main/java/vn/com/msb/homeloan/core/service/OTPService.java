package vn.com.msb.homeloan.core.service;

import vn.com.msb.homeloan.infras.configs.properties.OtpConfig;

public interface OTPService {

  void incrementSendOtpCounter(String redisKey, OtpConfig.RetryConfig retryConfig,
      boolean isUpdateTime);

  boolean resetCounter(String redisKey);

  OtpConfig.RetryConfig getResendOtpBlockTime(String redisKey);

  boolean isOtpBlock(String redisKey);
}
