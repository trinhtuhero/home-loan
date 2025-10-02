package vn.com.msb.homeloan.core.client;

import org.springframework.cloud.openfeign.FeignClient;
import vn.com.msb.homeloan.infras.configs.feign.EncoderFeignConfig;

@FeignClient(name = "common-cms-feign-client", url = "${feign.common.cms.url}", configuration = {
    EncoderFeignConfig.class})
public interface CommonCMSFeignClient {

}
