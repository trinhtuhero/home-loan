package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Ward {

  String code;
  String name;
  String districtCode;
  String provinceCode;
  String mobioDistrictCode;
  String mobioProvinceCode;
  String mobioCode;
  String mvalueCode;
  String mvalueName;
  String mvalueDistrictCode;
  String mvalueProvinceCode;
}
