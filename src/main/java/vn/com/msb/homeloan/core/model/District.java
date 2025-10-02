package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class District {

  String code;
  String name;
  String provinceCode;
  String mobioCode;
  String mobioName;
  String mobioProvinceCode;
  String mercuryName;
  String mvalueCode;
  String mvalueName;
  String mvalueProvinceCode;
}
