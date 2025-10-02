package vn.com.msb.homeloan.core.client;

import java.io.IOException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import vn.com.msb.homeloan.api.dto.response.ApiInternalResponse;

public interface CommonCMSRestClient {

  ResponseEntity<ApiInternalResponse> authorInfo(
      @RequestHeader(value = "Authorization") String authorization) throws IOException;
}
