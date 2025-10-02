package vn.com.msb.homeloan.api.dto.response.customer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryFullCustomerInfoRequest {

  InquiryCustomerInfo inquiryFullCustomerInfo;
}
