package vn.com.msb.homeloan.api.dto.request.customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountInfo {

  // Số tài khoản của khách hàng
  private String accountNo;
  // Tên khách hàng
  private String accountName;
  // Loại tài khoản khách hàng
  private String accountType;
  // Số dư khả dụng
  private String cbal;
  // Loại tiền tệ
  private String currency;
  // Số cif của khách hàng
  private String cifNumber;
  // Loại sản phẩm của tài khoản
  private String productType;
  // Có đồng sở hữu hay không
  private String relationShip;
  // Trạng thái tài khoản
  private String status;

}
