package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OTPCommonRequest {

  @NotEmpty
  private String otp;

  @NotEmpty
  private String uuid;
}
