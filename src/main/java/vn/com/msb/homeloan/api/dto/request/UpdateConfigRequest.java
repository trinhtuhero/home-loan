package vn.com.msb.homeloan.api.dto.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateConfigRequest {

  private String name;

  private String currentValue;
}
