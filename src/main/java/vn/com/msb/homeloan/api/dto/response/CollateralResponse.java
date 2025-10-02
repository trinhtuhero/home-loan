package vn.com.msb.homeloan.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class CollateralResponse {

  String uuid;
  String loanId;
  String type;
  String status;
  String fullName;
  String relationship;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  Long value;
  String location;
  String description;
}
