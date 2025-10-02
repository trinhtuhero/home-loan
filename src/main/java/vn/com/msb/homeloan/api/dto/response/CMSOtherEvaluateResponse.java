package vn.com.msb.homeloan.api.dto.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSOtherEvaluateResponse {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  String legalRecord;

  Long rmInputValue;

  String rmReview;

  String payerId;
}
