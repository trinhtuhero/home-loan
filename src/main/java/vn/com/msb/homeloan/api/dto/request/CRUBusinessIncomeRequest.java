package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.apache.logging.log4j.util.Strings;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.ErrorEnum;
import vn.com.msb.homeloan.core.exception.ApplicationException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CRUBusinessIncomeRequest {

  @JsonProperty("uuid")
  String uuid;

  @JsonProperty("loan_application_id")
  @NotBlank
  String loanApplicationId;

  @JsonProperty("owner_type")
  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @JsonProperty("business_type")
  @NotBlank
  @Pattern(regexp = "^(WITH_REGISTRATION|ENTERPRISE|WITHOUT_REGISTRATION)$", message = "must match pattern")
  String businessType;

  @JsonProperty("business_line")
  @NotBlank
  @Pattern(regexp = "^(SERVICES|TRADE|MINING|CONSTRUCTION|PRODUCTION|TRANSPORT|EDUCATION|REAL_ESTATE|ADMINISTRATION_AND_CAREER|BANKING_AND_FINANCE|INSURANCE|OTHERS)$", message = "must match pattern")
  String businessLine;

  @JsonProperty("business_code")
  String businessCode;

  @NotBlank
  @JsonProperty("name")
  String name;

  @NotBlank
  @JsonProperty("province")
  String province;

  @NotBlank
  @JsonProperty("province_name")
  String provinceName;

  @NotBlank
  @JsonProperty("district")
  String district;

  @NotBlank
  @JsonProperty("district_name")
  String districtName;

  @NotBlank
  @JsonProperty("ward")
  String ward;

  @NotBlank
  @JsonProperty("ward_name")
  String wardName;

  @NotBlank
  @JsonProperty("address")
  String address;

  @JsonProperty("phone")
  @Pattern(regexp = "^(^$|\\d+)$", message = "must match phone pattern")
  String phone;

  @NotNull
  @JsonProperty("value")
  String value;

  String payerId;

  public void validate() {
    try {
      Long.parseLong(value);
    } catch (NumberFormatException ex) {
      throw new ApplicationException(ErrorEnum.VALUE_INVALID);
    }
    List<String> list = new ArrayList<>(Arrays.asList(BusinessTypeEnum.ENTERPRISE.getCode(),
        BusinessTypeEnum.WITH_REGISTRATION.getCode()));
    if (list.contains(businessType) && (businessCode == null || Strings.isEmpty(businessCode))) {
      throw new ApplicationException(ErrorEnum.BUSINESS_CODE_IS_REQUIRED);
    }
  }
}
