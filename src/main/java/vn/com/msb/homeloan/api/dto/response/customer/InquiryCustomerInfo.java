package vn.com.msb.homeloan.api.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryCustomerInfo {

  private AuthenInfo authenInfo;
  private CommonInfo commonInfo;
  // Số cif của khách hàng
  private String cifNumber;
  private String cifNo;
  // Số giấy tờ tùy thân của khách hàng
  private String idNumber;
  //Loại giấy tờ tùy thân
  private String idType;
}
