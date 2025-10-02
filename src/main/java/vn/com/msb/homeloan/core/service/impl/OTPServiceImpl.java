package vn.com.msb.homeloan.core.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import vn.com.msb.homeloan.core.model.OtpBlockInfo;
import vn.com.msb.homeloan.core.service.OTPService;
import vn.com.msb.homeloan.core.service.RedisService;
import vn.com.msb.homeloan.core.util.DateUtils;
import vn.com.msb.homeloan.infras.configs.properties.OtpConfig;

import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class OTPServiceImpl implements OTPService {

  private final RedisService redisService;
  private final OtpConfig otpConfig;

  @Override
  public void incrementSendOtpCounter(String redisKey, OtpConfig.RetryConfig retryConfig,
      boolean isUpdateTime) {
    OtpBlockInfo blockInfo = redisService.get(redisKey);
    blockInfo = blockInfo == null ? new OtpBlockInfo() : blockInfo;

    if (retryConfig == null) {
      retryConfig = otpConfig.getRetryConfigs().get(0);
    }

    if (blockInfo.getBlockTime() != retryConfig.getBlockTime() || isUpdateTime) {
      Long blockDate = DateUtils.addToDate(new Date(), Calendar.SECOND, retryConfig.getBlockTime());
      blockInfo.setBlockDate(blockDate);
      blockInfo.setBlockTime(retryConfig.getBlockTime());
    }

    List<OtpConfig.RetryConfig> lstRetryConfig = otpConfig.getRetryConfigs().stream()
        .filter(OtpConfig.RetryConfig::getIsReset)
        .sorted(Comparator.comparing(OtpConfig.RetryConfig::getBlockTime).reversed())
        .collect(Collectors.toList());
    int expiredTime =
        CollectionUtils.isEmpty(lstRetryConfig) ? 1800 : lstRetryConfig.get(0).getBlockTime();

    if (blockInfo.getCount() == 0) {
      blockInfo.setCount(blockInfo.getCount() + 1);
      redisService.put(redisKey, blockInfo, expiredTime, TimeUnit.SECONDS);
    } else {
      blockInfo.setCount(blockInfo.getCount() + 1);
      redisService.update(redisKey, blockInfo, expiredTime, TimeUnit.SECONDS);
    }
  }

  @Override
  public boolean resetCounter(String redisKey) {
    boolean isDelete = redisService.remove(redisKey);
    log.info("Remove redis key: {}, result: {}", redisKey, isDelete);
    return isDelete;
  }

  @Override
  public OtpConfig.RetryConfig getResendOtpBlockTime(String redisKey) {
    OtpBlockInfo blockInfo = redisService.get(redisKey);
    if (blockInfo == null || blockInfo.getCount() == 0) {
      return null;
    }

    List<OtpConfig.RetryConfig> lstRetryConfig = otpConfig.getRetryConfigs().stream()
        .filter(r -> r.getCount() <= blockInfo.getCount())
        .sorted(Comparator.comparing(OtpConfig.RetryConfig::getCount).reversed())
        .collect(Collectors.toList());

    if (CollectionUtils.isEmpty(lstRetryConfig)) {
      return null;
    }
    return lstRetryConfig.get(0);
  }

  @Override
  public boolean isOtpBlock(String redisKey) {
    OtpBlockInfo blockInfo = redisService.get(redisKey);
    if (blockInfo == null) {
      return false;
    }
    long currentDate = (new Date()).getTime();
    return currentDate <= blockInfo.getBlockDate();
  }
}
