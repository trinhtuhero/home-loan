package vn.com.msb.homeloan.core.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

public class WebServletRequestReplacedFilter implements Filter {

  @Override
  public void destroy() {

  }

  @Override
  public void doFilter(ServletRequest request, ServletResponse response,
      FilterChain chain) throws IOException, ServletException {
    ServletRequest requestWrapper = null;
    if (request instanceof HttpServletRequest) {
      requestWrapper = new WebHttpServletRequestWrapper((HttpServletRequest) request);
    }
    //Get the stream in the request, convert the retrieved string into a stream again, and put it into the new request object.
    //Pass the new request object in the chain.doFiler method
    if (requestWrapper == null) {
      chain.doFilter(request, response);
    } else {
      chain.doFilter(requestWrapper, response);
    }
  }

  @Override
  public void init(FilterConfig arg0) throws ServletException {

  }
}