package vn.com.msb.homeloan.core.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.model.ErrorDetail;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ApplicationException extends RuntimeException {

  private Integer code;
  private String message;
  private String apiCode;
  private List<ErrorDetail> errors;

  public ApplicationException(ErrorEnum errorEnum, String... params) {
    this.code = errorEnum.getCode();
    ErrorDetail errorDetail = ErrorDetail.builder()
        .code(errorEnum.getCode())
        .message(String.format(errorEnum.getMessage(), params))
        .build();
    this.errors = new ArrayList<>();
    this.errors.add(errorDetail);
  }

  public ApplicationException(Integer code, String message) {
    this.code = code;
    ErrorDetail errorDetail = ErrorDetail.builder()
        .code(code)
        .message(message)
        .build();
    this.errors = new ArrayList<>();
    this.errors.add(errorDetail);
  }

  public ApplicationException(ErrorEnum errorEnum, Map<String, String> detail) {
    this.code = errorEnum.getCode();
    ErrorDetail errorDetail = ErrorDetail.builder()
        .code(errorEnum.getCode())
        .message(errorEnum.getMessage())
        .detail(detail)
        .build();
    this.errors = new ArrayList<>();
    this.errors.add(errorDetail);
  }

  public ApplicationException(List<ErrorEnum> errorEnums, Object... params) {
    this.errors = new ArrayList<>();
    int size = errorEnums.size();
    for (int i = 0; i < size; i++) {
      this.code = errorEnums.get(i).getCode();
      ErrorDetail errorDetail = ErrorDetail.builder()
          .code(errorEnums.get(i).getCode())
          .message(errorEnums.get(i).getMessage())
          .detail(i < params.length ? params[i] : null)
          .build();
      this.errors.add(errorDetail);
    }
  }

}
