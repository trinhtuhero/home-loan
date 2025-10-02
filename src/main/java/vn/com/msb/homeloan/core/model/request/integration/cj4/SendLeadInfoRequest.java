package vn.com.msb.homeloan.core.model.request.integration.cj4;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.class)
public class SendLeadInfoRequest {

  String fullName;
  String phoneNumber;
  String email;
  String dob;
  String address;
  String idNumber;
  String idIssuedDate;
  String idIssuedPlace;
  String gender;
  Long monthlyIncome;
  String maritalStatus;
  Integer isInterested;
  Integer isReferral;
  Integer numberOfDependents;
  String provinceId;
  String districtId;
  String wardId;
  String loanId;
  String requestDate;
  String employeeId;
  String channel;
  String token;
}
