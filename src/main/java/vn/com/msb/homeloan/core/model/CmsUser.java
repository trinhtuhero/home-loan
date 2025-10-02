package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CmsUserStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsUser {

  String emplId;

  String fullName;

  String email;

  String branchCode;

  String branchName;

  String leaderEmplId;

  String phone;

  String area;

  String leaderEmail;

  String error;

  String createdBy;

  String updatedBy;

  CmsUserStatusEnum status;

  String role;
}
