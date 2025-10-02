package vn.com.msb.homeloan.api.dto.response;

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
public class CollateralInfoResponse {

  List<CMSCollateralResponse> collaterals;
  List<CMSCollateralOwnerResponse> collateralOwners;
}
