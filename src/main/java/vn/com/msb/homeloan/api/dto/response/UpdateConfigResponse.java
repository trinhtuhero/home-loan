package vn.com.msb.homeloan.api.dto.response;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UpdateConfigResponse {

  private String name;

  private String jsonConfig;
}
