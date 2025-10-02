package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginProfileInfo {

  private String uuid;
  private String otpUuid;
  private String phone;
  private String fullName;
  private String gender;
  private String nationality;
  private String email;
  private String idNo;
  private Date issuedOn;
  private String placeOfIssue;
  private String oldIdNo;
}
