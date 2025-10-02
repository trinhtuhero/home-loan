package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUCreditCardRequest {

  String uuid;

  @NotBlank
  String loanId;

  @NotBlank
  @Pattern(regexp = "^(PRIMARY_CARD|SECONDARY_CARD)$", message = "must match pattern")
  String cardPriority;

  String type;

  @NotNull
  Long creditLimit;

  @NotBlank
  String object;

  @NotBlank
  String nameInCard;

  String firstPrimarySchool;

  Boolean autoDebit;

  @Pattern(regexp = "^(MINIMUM_PAYMENT|STATEMENT_BALANCE)$", message = "must match pattern")
  String debtDeductionRate;

  String debitAccountNumber;

  String receivingAddress;

  String parentId;
}
