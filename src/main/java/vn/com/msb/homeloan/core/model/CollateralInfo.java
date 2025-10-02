package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollateralInfo {

  List<Collateral> collaterals;
  List<CollateralOwner> collateralOwners;
}
