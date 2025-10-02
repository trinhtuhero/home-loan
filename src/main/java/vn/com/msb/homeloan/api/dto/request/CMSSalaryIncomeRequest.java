package vn.com.msb.homeloan.api.dto.request;

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
public class CMSSalaryIncomeRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @Pattern(regexp = "^(MSB|OTHERS|CASH)$", message = "must match pattern")
  @NotBlank
  String paymentMethod;

  @NotBlank
  String officeName;

  //@Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  @Pattern(regexp = "^(^$|\\d+)$", message = "must match phone pattern")
  String officePhone;

  @NotBlank
  String officeProvince;

  @NotBlank
  String officeProvinceName;

  @NotBlank
  String officeDistrict;

  @NotBlank
  String officeDistrictName;

  @NotBlank
  String officeWard;

  @NotBlank
  String officeWardName;

  String officeAddress;

  @NotBlank
  String officeTitle;

  @NotNull
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long value;

  @NotBlank
  String startWorkDate;

  String taxCode;

  String insuranceNo;

  @Pattern(regexp = "^(KHONG_THOI_HAN|BA_NAM|MOT_DEN_BA_NAM|SAU_THANG_DEN_MOT_NAM|DUOI_6_THANG|KHONG_CO)$", message = "must match pattern")
  String kindOfContract;

  @NotBlank
  @Pattern(regexp = "^(MSB|OTHERS)$", message = "must match pattern")
  String company;

  @Pattern(regexp = "^(BAND_1|BAND_2|BAND_3|BAND_4|BAND_5|BAND_6|BAND_7|BAND_8|BAND_9)$", message = "must match pattern")
  String band;

  @Pattern(regexp = "^(E|S|A_PLUS|A|B)$", message = "must match pattern")
  String kpiRate;

  String emplCode;

  @Pattern(regexp = "^(TALENT_POOL|KEY_PERSON|OTHERS)$", message = "must match pattern")
  String specialGroup;

  String rmReview;

  @Pattern(regexp = "^(GE1|GE2|AA1|AA2|BB1|BB2|C_PLUS)$", message = "must match pattern")
  String workGrouping;

  String payerId;

  Boolean msbStaff;
}
