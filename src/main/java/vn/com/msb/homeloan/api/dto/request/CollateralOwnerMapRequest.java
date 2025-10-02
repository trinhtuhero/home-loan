package vn.com.msb.homeloan.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CollateralOwnerMapRequest {

  private String collateralOwnerId;
  private String type;
}
