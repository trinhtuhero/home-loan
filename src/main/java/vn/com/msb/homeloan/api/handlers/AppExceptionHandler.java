package vn.com.msb.homeloan.api.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.logging.LogLevel;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import vn.com.msb.homeloan.api.dto.ApiResource;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;
import vn.com.msb.homeloan.core.model.ErrorDetail;
import vn.com.msb.homeloan.core.util.CustomLogUtil;
import vn.com.msb.homeloan.core.util.LogInfo;
import vn.com.msb.homeloan.core.util.StringUtils;
import vn.com.msb.homeloan.core.util.nofify.TeamNotifyUtil;
import vn.com.msb.homeloan.infras.configs.properties.EnvironmentProperties;

@RestControllerAdvice
@RequiredArgsConstructor
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  private final EnvironmentProperties envProperties;
  private final ObjectMapper objectMapper;

  private final TeamNotifyUtil teamNotifyUtil;
  @Qualifier("fixedThreadPool")
  private final ExecutorService executorService;

  @ExceptionHandler(value = Exception.class)
  public ResponseEntity<ApiResource> handleException(Exception ex, HttpServletRequest httpRequest) {
    List<ErrorDetail> errorDetails = new ArrayList<>();
    errorDetails.add(ErrorDetail.builder()
        .code(ErrorEnum.SERVER_ERROR.getCode())
        .message(ErrorEnum.SERVER_ERROR.getMessage())
        .build());
    ApiResource apiResponse = new ApiResource(ErrorEnum.SERVER_ERROR.getCode(), errorDetails);

    // write to log
    log.error("{} - {}", CustomLogUtil.writeValueAsString(objectMapper,
        new LogInfo(envProperties.getApplicationName(), envProperties.getApplicationVersion(),
            LogLevel.ERROR, httpRequest,
            apiResponse)), ex);

    // noti
    executorService.execute(() -> {
      teamNotifyUtil.send(ExceptionUtils.getStackTrace(ex));
    });

    return new ResponseEntity<>(apiResponse, HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = ApplicationException.class)
  public ResponseEntity<ApiResource> handleValidationException(ApplicationException ex,
      HttpServletRequest httpRequest) {
    HttpStatus status = getStatus(ex);
    ApiResource apiResource = new ApiResource(status.value(), ex.getErrors());
    log.error("{}", CustomLogUtil.writeValueAsString(objectMapper,
        new LogInfo(envProperties.getApplicationName(), envProperties.getApplicationVersion(),
            LogLevel.ERROR, httpRequest,
            apiResource)));
    return new ResponseEntity<>(apiResource, status);
  }

  @ExceptionHandler(value = ConstraintViolationException.class)
  public ResponseEntity<ApiResource> handleValidationException(ConstraintViolationException ex,
      HttpServletRequest httpRequest) {
    String detailMessage = ex.getMessage();
    Map<String, String> mapDetail = new HashMap<>();
    List<ErrorDetail> errorDetails = new ArrayList<>();
    try {
      if (!StringUtils.isEmpty(detailMessage)) {
        String[] detailMessages = detailMessage.split(",");
        for (String ms : detailMessages) {
          String[] mss = ms.split(":");
          if (mss.length > 1) {
            mapDetail.put(StringUtils.trim(mss[0]), StringUtils.trim(mss[1]));
          }
        }
      }

      errorDetails.add(ErrorDetail.builder()
          .code(ErrorEnum.INVALID_FORM.getCode())
          .message(ErrorEnum.INVALID_FORM.getMessage())
          .detail(mapDetail)
          .build());
      ApiResource apiResource = new ApiResource(HttpStatus.BAD_REQUEST.value(), errorDetails);
      return new ResponseEntity<>(apiResource, HttpStatus.BAD_REQUEST);
    } catch (Exception ignored) {
    }
    return new ResponseEntity<>(new ApiResource(), HttpStatus.BAD_REQUEST);
  }

  @Override
  public ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
      HttpHeaders headers, HttpStatus status, WebRequest request) {
    try {
      if (!ex.getBindingResult().hasErrors()) {
        return this.handleExceptionInternal(ex, (Object) null, headers, status, request);
      }

      List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
      Map<String, String> mapDetail = new HashMap<>();
      for (ObjectError objectError : objectErrors) {
        if (objectError != null && objectError.getArguments() != null
            && objectError.getArguments().length > 0
            && objectError.getArguments()[0] instanceof DefaultMessageSourceResolvable) {
          String key = String.format("%s.%s", objectError.getCode(),
              ((DefaultMessageSourceResolvable) objectError.getArguments()[0]).getDefaultMessage());
          mapDetail.put(convertToCamelCase(key), objectError.getDefaultMessage());
        }
      }

      List<ErrorDetail> errorDetails = new ArrayList<>();
      errorDetails.add(ErrorDetail.builder()
          .code(ErrorEnum.INVALID_FORM.getCode())
          .message(ErrorEnum.INVALID_FORM.getMessage())
          .detail(mapDetail)
          .build());

      ApiResource apiResource = new ApiResource(HttpStatus.BAD_REQUEST.value(), errorDetails);
      return new ResponseEntity<>(apiResource, HttpStatus.BAD_REQUEST);
    } catch (Exception e) {
      return this.handleExceptionInternal(ex, (Object) null, headers, status, request);
    }
  }

  private String convertToCamelCase(String str) {
    if (StringUtils.isEmpty(str)) {
      return "";
    }
    String regex = "([a-z])([A-Z]+)";
    String replacement = "$1_$2";
    return str.replaceAll(regex, replacement)
        .toLowerCase();
  }

  private HttpStatus getStatus(ApplicationException ex) {
    if (ex.getCode() == null) {
      return HttpStatus.BAD_REQUEST;
    }
    try {
      int status = Integer.parseInt(String.valueOf(ex.getCode()).substring(0, 3));
      return HttpStatus.valueOf(status);
    } catch (Exception e) {
      return HttpStatus.BAD_REQUEST;
    }
  }
}
