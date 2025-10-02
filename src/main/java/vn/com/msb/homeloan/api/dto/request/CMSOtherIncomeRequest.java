package vn.com.msb.homeloan.api.dto.request;

import java.math.BigDecimal;
import java.util.List;
import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
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
public class CMSOtherIncomeRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @NotBlank
  @Pattern(regexp = "^(SAVING_ACCOUNTS|RENTAL_PROPERTIES|OTHERS|GOM_XAY)$", message = "must match pattern")
  String incomeFrom;

  @NotNull
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long value;

  String rmReview;

  String payerId;

  //Địa chỉ tài sản/Loại xe
  String rentProperty;

  //Bên thuê
  String tenant;

  //Số điện thoại Bên thuê
  String tenantPhone;

  //Mục đích thuê
  String tenantPurpose;

  //Giá cho thuê
  Long tenantPrice;

  BigDecimal incomePerMonth;

  Integer quantity;

  List<LandTransactionRequest> landTransactions;
}
