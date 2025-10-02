package vn.com.msb.homeloan.core.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.model.UserToken;

@Slf4j
@Component
public class KeycloakJwtTokenProvider {

  private final String publicKey;

  public KeycloakJwtTokenProvider(@Value("${msb.common.authen.public-key}") String publicKey) {
    this.publicKey = publicKey;
  }

  /**
   * Returns the user id encapsulated within the token
   */
  public UserToken getUserTokenFromJWT(String token) {
    try {
      final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
          Base64.getDecoder().decode(publicKey));
      final KeyFactory kf = KeyFactory.getInstance("RSA");
      final PublicKey publicKey = kf.generatePublic(keySpec);
      Claims claims = Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(token)
          .getBody();

      UserToken userToken = new UserToken();
      userToken.setEmail(claims.get("email", String.class));
      userToken.setUserId(claims.get("empl_id", String.class));
      userToken.setToken(token);
      return userToken;
    } catch (Exception ex) {
      return null;
    }
  }
}
