package vn.com.msb.homeloan.api.aop;

import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class TimeMonitorAspect {

  @Around("@annotation(TimeMonitor)")
  public Object logTime(ProceedingJoinPoint joinPoint) throws Throwable {
    log.info("Starting " + joinPoint.getSignature().toShortString());
    log.info(
        joinPoint.getSignature().getName() + " input -> " + Arrays.toString(joinPoint.getArgs()));
    long start = System.nanoTime();
    Object proceed = joinPoint.proceed();
    long executionTime = System.nanoTime() - start;
    log.info(joinPoint.getSignature().getName() + "ed -> " + Arrays.toString(joinPoint.getArgs()));
    log.info(joinPoint.getSignature().getName() + " took: " + executionTime + " ns\n");
    return proceed;
  }
}
