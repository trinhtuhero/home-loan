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
public class Province {

  String code;
  String name;
  String mobioCode;
  String mercuryName;
  String mvalueCode;
  String mvalueName;
}
