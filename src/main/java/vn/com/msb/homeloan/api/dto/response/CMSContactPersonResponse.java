package vn.com.msb.homeloan.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CMSContactPersonResponse {

  String uuid;
  String loanId;
  ContactPersonTypeEnum type;
  String fullName;
  String phone;
}
