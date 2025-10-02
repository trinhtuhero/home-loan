package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginProfileInfoResponse {

  private String uuid;
  private String otpUuid;
  private String phone;
  private String fullName;
  private String gender;
  private String nationality;
  private String email;
  private String idNo;
  private String issuedOn;
  private String placeOfIssue;
  private String oldIdNo;
}
