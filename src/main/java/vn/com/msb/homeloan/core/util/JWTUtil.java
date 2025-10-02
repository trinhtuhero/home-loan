package vn.com.msb.homeloan.core.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.model.response.JwtPayloadDTO;

@Slf4j
public class JWTUtil {

  public static JwtPayloadDTO decodeJWT(String accessToken) {
    String[] split_string = accessToken.split("\\.");
    String base64EncodedHeader = split_string[0];
    String base64EncodedBody = split_string[1];
    String base64EncodedSignature = split_string[2];

    org.apache.commons.codec.binary.Base64 base64Url = new org.apache.commons.codec.binary.Base64(
        true);
    String body = new String(base64Url.decode(base64EncodedBody));
    Gson g = new Gson();
    JwtPayloadDTO jwtPayloadDTO = g.fromJson(body, JwtPayloadDTO.class);
    return jwtPayloadDTO;
  }

  public static String getJwtStr(String bearerToken) {
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(
        Constants.HEADER_AUTHORIZATION_BEARER)) {
      log.info("Extracted Token: " + bearerToken);
      return bearerToken.replace(Constants.HEADER_AUTHORIZATION_BEARER, "");
    }
    return null;
  }
}
