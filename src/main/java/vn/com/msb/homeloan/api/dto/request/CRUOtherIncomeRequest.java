package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUOtherIncomeRequest {

  @JsonProperty("uuid")
  String uuid;

  @JsonProperty("loan_application_id")
  @NotBlank
  String loanApplicationId;

  @JsonProperty("owner_type")
  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @JsonProperty("income_from")
  @NotBlank
  @Pattern(regexp = "^(SAVING_ACCOUNTS|RENTAL_PROPERTIES|OTHERS|GOM_XAY)$", message = "must match pattern")
  String incomeFrom;

  @JsonProperty("value")
  @NotBlank
  String value;

  String payerId;

  BigDecimal incomePerMonth;

  Integer quantity;


  public void validate() {
    try {
      Long.parseLong(value);
    } catch (NumberFormatException ex) {
      throw new ApplicationException(ErrorEnum.VALUE_INVALID);
    }
  }
}
