package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanApplicationItemRequest {

  String uuid;

  @NotNull
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(LAND|TIEU_DUNG|XAY_SUA_NHA|HO_KINH_DOANH)$", message = "must match LAND|TIEU_DUNG|XAY_SUA_NHA|HO_KINH_DOANH")
  String loanPurpose;

  @NotNull
  @Min(1)
  Long loanAssetValue;

  @NotNull
  @Min(1)
  Long loanAmount;

  @NotNull
  @Min(3)
  @Max(420)
  Integer loanTime;
}
