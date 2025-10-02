package vn.com.msb.homeloan.infras.configs;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Http401UnauthorizedEntryPoint implements AuthenticationEntryPoint {

  /**
   * Always returns a 401 error code to the client.
   */
  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response,
      AuthenticationException arg2) throws IOException,
      ServletException {

    log.debug("Pre-authenticated entry point called. Rejecting access");
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    log.info("Authentication Failed: {}" + arg2.getMessage());
    response.getOutputStream().println(
        "{\"errors\": [{\"code\": 401001,\"message\": \"authentication error\"}],\"status\": 401}");
  }
}
