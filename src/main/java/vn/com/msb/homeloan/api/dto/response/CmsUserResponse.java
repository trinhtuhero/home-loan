package vn.com.msb.homeloan.api.dto.response;

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
public class CmsUserResponse {

  String fullName;
  String email;
  String branchCode;
  String branchName;
  String phone;
}
