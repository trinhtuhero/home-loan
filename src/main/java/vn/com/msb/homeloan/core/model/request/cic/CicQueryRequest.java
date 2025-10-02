package vn.com.msb.homeloan.core.model.request.cic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.msb.homeloan.core.constant.cic.CustomerType;
import vn.com.msb.homeloan.core.constant.cic.ProductCode;

/**
 * CIC query request model
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CicQueryRequest {

  private String cicCode;
  private String customerUniqueId;
  private CustomerType customerType;
  private ProductCode productCode;
  private int validityPeriod;
  private String address;
  private String customerName;
  private String userNameDirectRq;
  private String branchCode;
  private String dealingRoomCode;
}
