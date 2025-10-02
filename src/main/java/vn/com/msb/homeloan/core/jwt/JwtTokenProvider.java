package vn.com.msb.homeloan.core.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.entity.ProfileEntity;
import vn.com.msb.homeloan.core.model.UserToken;
import vn.com.msb.homeloan.core.service.RedisService;

@Slf4j
@Component
public class JwtTokenProvider {

  private final String jwtSecret;
  private final long jwtExpirationInMs;

  @Autowired
  RedisService redisService;

  public JwtTokenProvider(@Value("${msb.jwt.secret}") String jwtSecret,
      @Value("${msb.jwt.expiration}") long jwtExpirationInMs) {
    this.jwtSecret = jwtSecret;
    this.jwtExpirationInMs = jwtExpirationInMs;
  }

  /**
   * Generates a token from a principal object. Embed the refresh token in the jwt so that a new jwt
   * can be created
   */
  public String generateToken(ProfileEntity customer) {
    Instant expiryDate = Instant.now().plusSeconds(jwtExpirationInMs);
    return Jwts.builder()
        .setSubject(customer.getPhone())
        .claim(Constants.JWT_KEY_USER_ID, customer.getUuid())
        .claim(Constants.JWT_KEY_PHONE_NUMBER, customer.getPhone())
        .claim(Constants.JWT_KEY_IDENTIFICATION_NUMBER, customer.getIdNo())
        .claim(Constants.JWT_KEY_UUID, UUID.randomUUID())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(expiryDate))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  public String generateTokenWithExpire(ProfileEntity customer, long jwtExpirationInMs) {
    Instant expiryDate = Instant.now().plusSeconds(jwtExpirationInMs);
    return Jwts.builder()
        .setSubject(customer.getPhone())
        .claim(Constants.JWT_KEY_USER_ID, customer.getUuid())
        .claim(Constants.JWT_KEY_PHONE_NUMBER, customer.getPhone())
        .claim(Constants.JWT_KEY_IDENTIFICATION_NUMBER, customer.getIdNo())
        .claim(Constants.JWT_KEY_UUID, UUID.randomUUID())
        .setIssuedAt(Date.from(Instant.now()))
        .setExpiration(Date.from(expiryDate))
        .signWith(SignatureAlgorithm.HS512, jwtSecret)
        .compact();
  }

  /**
   * Returns the user id encapsulated within the token
   */
  public UserToken getUserTokenFromJWT(String token) {
    Claims claims = Jwts.parser()
        .setSigningKey(jwtSecret)
        .parseClaimsJws(token)
        .getBody();

    UserToken userToken = new UserToken();
    userToken.setUserId(claims.get(Constants.JWT_KEY_USER_ID, String.class));
    userToken.setPhoneNumber(claims.get(Constants.JWT_KEY_PHONE_NUMBER, String.class));
    userToken.setIdNumber(claims.get(Constants.JWT_KEY_IDENTIFICATION_NUMBER, String.class));
    userToken.setUuid(claims.get(Constants.JWT_KEY_UUID, String.class));

    return userToken;
  }

  /**
   * Validates if a token satisfies the following properties - Signature is not malformed - Token
   * hasn't expired - Token is supported - Token has not recently been logged out.
   */
  public boolean validateToken(String authToken) {
    try {
      Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);

      UserToken userToken = getUserTokenFromJWT(authToken);
      if (!redisService.isExpired(userToken.getUserId() + "-" + userToken.getUuid())) {
        int tokenStatus = redisService.get(userToken.getUserId() + "-" + userToken.getUuid());
        if (tokenStatus == Constants.STATUS_INACTIVE) {
          log.error("JWT token has been logged out");
          return false;
        }
      }
      return true;
    } catch (MalformedJwtException ex) {
      log.error("Invalid JWT token");
    } catch (ExpiredJwtException ex) {
      log.error("Expired JWT token");
    } catch (UnsupportedJwtException ex) {
      log.error("Unsupported JWT token");
    } catch (IllegalArgumentException ex) {
      log.debug("JWT claims string is empty.");
    }
    return false;
  }
}
