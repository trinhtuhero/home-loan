package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Accessors(chain = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetail {

  private int code;
  private String message;
  // private Map<String, String> detail;
  private Object detail;

  public ErrorDetail(int code, String message) {
    this.code = code;
    this.message = message;
  }
}
