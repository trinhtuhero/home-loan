package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUOtherIncomeResponse {

  String uuid;

  @JsonProperty("loan_application_id")
  String loanApplicationId;

  @JsonProperty("owner_type")
  OwnerTypeEnum ownerType;

  @JsonProperty("income_from")
  IncomeFromEnum incomeFrom;

  @JsonProperty("value")
  String value;

  String payerId;
}
