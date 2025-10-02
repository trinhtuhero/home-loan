package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Organization {

  String code;
  String name;
  String specializedBank;
  String parent;
  String type;
  String ecmCode;
  String status;
}
