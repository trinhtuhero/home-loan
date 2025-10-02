package vn.com.msb.homeloan.core.model;

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
public class CmsUserInfo {

  String fullName;

  String email;

  String phone;

  String branchCode;

  String branchName;

  String leader;

  String updatedAt;

  String updatedBy;

  String status;

  String role;
}
