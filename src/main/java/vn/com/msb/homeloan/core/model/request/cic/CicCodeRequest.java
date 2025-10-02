package vn.com.msb.homeloan.core.model.request.cic;

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
public class CicCodeRequest {

  private String customerUniqueId;
  private CustomerType customerType;
}
