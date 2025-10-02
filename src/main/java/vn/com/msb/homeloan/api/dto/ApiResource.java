package vn.com.msb.homeloan.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.ErrorDetail;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class ApiResource {

  private Object data;

  private int status;

  private String apiCode;

  private List<ErrorDetail> errors;

  public ApiResource(Object data, int status) {
    this.status = status;
    this.data = data;
    this.apiCode = null;
    this.errors = null;
  }

  public ApiResource(int status, List<ErrorDetail> errors) {
    this.status = status;
    this.data = null;
    this.apiCode = null;
    this.errors = errors;
  }
}
