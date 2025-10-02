package vn.com.msb.homeloan.core.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class OtherEvaluate {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  String legalRecord;

  Long rmInputValue;

  String rmReview;

  String payerId;
}


