package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserToken {

  private String userId;
  private String phoneNumber;
  private String idNumber;
  private String uuid;
  private String email;
  private String token;

}
