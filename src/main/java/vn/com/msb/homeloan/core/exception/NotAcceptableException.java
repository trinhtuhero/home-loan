package vn.com.msb.homeloan.core.exception;

import lombok.Getter;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.ErrorEnum;

@Getter
@Setter
public class NotAcceptableException extends ApplicationException {

  public NotAcceptableException(ErrorEnum errorEnum, String... prams) {
    super(errorEnum, prams);
  }
}
