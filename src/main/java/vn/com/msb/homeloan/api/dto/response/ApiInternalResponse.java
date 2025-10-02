package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import vn.com.msb.homeloan.core.model.ErrorDetail;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
public class ApiInternalResponse<T> {

  private T data;
  private int status;
  private String message;
  private String apiCode;
  private List<ErrorDetail> errors;

  public ApiInternalResponse(int status) {
    this.status = status;
  }

  public ApiInternalResponse(int status, String message) {
    this.status = status;
    this.message = message;
  }

  public ApiInternalResponse(int status, int errorCode, String errorMessage) {
    this.status = status;
    this.data = null;
    this.apiCode = null;
    this.errors = new ArrayList<>();
    this.errors.add(new ErrorDetail(errorCode, errorMessage));
  }
}
