package vn.com.msb.homeloan.api.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import vn.com.msb.homeloan.infras.configs.APITokenUser;

public class APIKeyAuthenticationFilter extends OncePerRequestFilter {

  private AuthenticationManager authenticationManager;

  public APIKeyAuthenticationFilter(AuthenticationManager authenticationManager) {
    this.authenticationManager = authenticationManager;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest httpServletRequest,
      HttpServletResponse httpServletResponse, FilterChain filterChain)
      throws ServletException, IOException {
    String auth = httpServletRequest.getHeader("Authorization");

    if (auth != null && auth.startsWith("ApiKey")) {
      Authentication authentication = authenticationManager.authenticate(
          new APITokenUser(null, auth));
      if (authentication.isAuthenticated()) {
        SecurityContextHolder.getContext().setAuthentication(authentication);
      } else {
        SecurityContextHolder.clearContext();
      }
    }

    filterChain.doFilter(httpServletRequest, httpServletResponse);
  }
}