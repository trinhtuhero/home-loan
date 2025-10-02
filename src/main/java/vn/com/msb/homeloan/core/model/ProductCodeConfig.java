package vn.com.msb.homeloan.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProductCodeConfig {

  String uuid;

  String productCode;

  Integer from;

  Integer to;

  String approvalFlow;

  String incomeMethod;

  String productType;

  String expression;
}
