package vn.com.msb.homeloan.core.model.cic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.msb.homeloan.core.constant.cic.CustomerType;

/**
 * CIC code request model
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CicInformation {

  private String customerUniqueId;
  private CustomerType customerType;
  private String customerName;

  public CicInformation(String customerUniqueId, String customerName) {
    this.customerUniqueId = customerUniqueId;
    this.customerName = customerName;
    this.customerType = CustomerType.INDIVIDUAL;
  }
}
