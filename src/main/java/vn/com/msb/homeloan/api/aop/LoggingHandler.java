package vn.com.msb.homeloan.api.aop;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.boot.logging.LogLevel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.util.LogInfo;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class LoggingHandler {

  private final ObjectMapper objectMapper;
  private final EnvironmentProperties appConfig;

  @Pointcut("execution(public * vn.com.msb.homeloan.api.controllers.external..*.*(..))")
  public void controller() {
  }

  @Pointcut("execution(* *.*(..))")
  protected void allMethod() {
  }

  @Pointcut("execution(public * *(..))")
  protected void loggingPublicOperation() {
  }

  @Pointcut("execution(* *.*(..))")
  protected void loggingAllOperation() {
  }

  @Pointcut("within(org.learn.log..*)")
  private void logAnyFunctionWithinResource() {
  }

  @AfterReturning(pointcut = "controller() && allMethod()", returning = "result")
  public void logAfter(JoinPoint joinPoint, Object result) {
    try {
      ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
      if (servletRequestAttributes != null) {
        HttpServletRequest request = servletRequestAttributes.getRequest();
        ApiResource returnValue = this.getValue(result);
        if (returnValue != null) {
          LogInfo logInfo = new LogInfo(appConfig.getApplicationName(),
              appConfig.getApplicationVersion(), LogLevel.INFO, request, returnValue);
          log.info(objectMapper.writeValueAsString(logInfo));
        }
      }
    } catch (Exception ignored) {
    }
  }

  private ApiResource getValue(Object result) {
    if (result instanceof ResponseEntity
        && ((ResponseEntity<?>) result).getBody() instanceof ApiResource) {
      return (ApiResource) ((ResponseEntity<?>) result).getBody();
    }
    return null;
  }
}
