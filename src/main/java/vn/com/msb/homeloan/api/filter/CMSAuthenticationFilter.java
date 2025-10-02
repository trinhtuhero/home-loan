package vn.com.msb.homeloan.api.filter;

import io.jsonwebtoken.Jwts;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.jwt.KeycloakJwtTokenProvider;
import vn.com.msb.homeloan.core.model.CustomUserDetails;
import vn.com.msb.homeloan.core.model.UserToken;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@Slf4j
@RequiredArgsConstructor
public class CMSAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private EnvironmentProperties envProperties;

  @Autowired
  private KeycloakJwtTokenProvider keycloakJwtTokenProvider;

  /**
   * Filter the incoming request for a valid token in the request header
   */
  @SneakyThrows
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    String jwt = getJwtFromRequest(request);
    try {
      if (StringUtils.hasText(jwt) && verifyToken(jwt)) {
        log.debug("Verify cms token successfully");

        UserToken userToken = keycloakJwtTokenProvider.getUserTokenFromJWT(jwt);

        CustomUserDetails userDetails = new CustomUserDetails(userToken);
        UsernamePasswordAuthenticationToken
            authentication = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception ex) {
      log.error("Failed to set user authentication in security context: ", ex);
      throw ex;
    }
    filterChain.doFilter(request, response);
  }

  /**
   * Extract the token from the Authorization request header
   */
  private String getJwtFromRequest(HttpServletRequest request) {
    String bearerToken = request.getHeader(Constants.HEADER_AUTHORIZATION);
    if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(
        Constants.HEADER_AUTHORIZATION_BEARER)) {
      log.debug("Extracted Token: " + bearerToken);
      return bearerToken.replace(Constants.HEADER_AUTHORIZATION_BEARER, "");
    }
    return null;
  }

  private boolean verifyToken(final String accessToken) {
    try {
      final X509EncodedKeySpec keySpec = new X509EncodedKeySpec(
          Base64.getDecoder().decode(envProperties.getPublicKey()));
      final KeyFactory kf = KeyFactory.getInstance("RSA");
      final PublicKey publicKey = kf.generatePublic(keySpec);
      Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(accessToken);
      return true;
    } catch (Exception ex) {
      return false;
    }
  }
}
