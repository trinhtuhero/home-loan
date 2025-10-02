package vn.com.msb.homeloan.api.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.msb.homeloan.core.constant.Constants;
import vn.com.msb.homeloan.core.jwt.JwtTokenProvider;
import vn.com.msb.homeloan.core.model.CustomUserDetails;
import vn.com.msb.homeloan.core.model.UserToken;
import vn.com.msb.homeloan.core.service.RedisService;

@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private RedisService redisService;

  /**
   * Filter the incoming request for a valid token in the request header
   */
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException {
    try {
      String jwt = getJwtFromRequest(request);
      if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt) && !inBlacklist(jwt)) {
        UserToken user = jwtTokenProvider.getUserTokenFromJWT(jwt);
        CustomUserDetails userDetails = new CustomUserDetails(user);
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

  /**
   * Check token in blaklist
   */
  private boolean inBlacklist(String token) {
    String key = Constants.PREFIX_BLACKLIST + token;
    return redisService.hasKey(key);
  }
}
