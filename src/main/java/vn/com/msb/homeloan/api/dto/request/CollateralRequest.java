package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CollateralRequest {

  String uuid;
  @NotBlank
  String loanId;

  @NotBlank
  @Pattern(regexp = "^(ND|CC|PTVT|GTCG)$")
  String type;

  @NotBlank
  @Pattern(regexp = "^(ME|COUPLE|THIRD_PARTY|SPOUSE|OTHERS)$")
  String status;

  String fullName;

  String relationship;

  String province;

  String provinceName;

  String district;

  String districtName;

  String ward;

  String wardName;

  String address;

  @Min(0)
  Long value;

  String location;
  String description;
}
