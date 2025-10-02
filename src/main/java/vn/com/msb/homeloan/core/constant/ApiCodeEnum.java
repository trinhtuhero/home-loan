package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

@Getter
public enum ApiCodeEnum {

  // http status 404 error
  IT_SERVICE_ERROR("IT_SERVICE_ERROR"),

  //TODO: change to name of your service
  API_SERVICE_ERROR("HOME_LOAN_SERVICE_ERROR");

  private final String apiCode;

  ApiCodeEnum(String apiCode) {
    this.apiCode = apiCode;
  }

}
