package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CollateralOwnerMapTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollateralOwnerMap {

  private String loanId;
  private String collateralOwnerId;
  private String collateralId;
  private CollateralOwnerMapTypeEnum type;
}
