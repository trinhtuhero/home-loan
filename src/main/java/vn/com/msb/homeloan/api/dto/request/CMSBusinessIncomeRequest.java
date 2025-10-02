package vn.com.msb.homeloan.api.dto.request;

import java.math.BigDecimal;
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
import vn.com.msb.homeloan.api.validation.CustomDateConstraint;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSBusinessIncomeRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  @NotBlank
  @Pattern(regexp = "^(WITH_REGISTRATION|ENTERPRISE|WITHOUT_REGISTRATION)$", message = "must match pattern")
  String businessType;

  @NotBlank
  @Pattern(regexp = "^(SERVICES|TRADE|MINING|CONSTRUCTION|PRODUCTION|TRANSPORT|EDUCATION|REAL_ESTATE|ADMINISTRATION_AND_CAREER|BANKING_AND_FINANCE|INSURANCE|OTHERS)$", message = "must match pattern")
  String businessLine;

  String businessCode;

  @NotBlank
  String name;

  @NotBlank
  String province;

  @NotBlank
  String provinceName;

  @NotBlank
  String district;

  @NotBlank
  String districtName;

  @NotBlank
  String ward;

  @NotBlank
  String wardName;

  String address;

  @Pattern(regexp = "^(^$|\\d+)$", message = "must match phone pattern")
  String phone;

  @NotNull
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long value;

  //ngành nghề kinh doanh
  @NotBlank
  String businessActivity;

  @CustomDateConstraint
  String issuedDate;

  String placeOfIssue;

  @CustomDateConstraint
  String businessStartDate;

  BigDecimal capitalRatio;

  //hình thức sở hữu
  @NotBlank
  @Pattern(regexp = "^(PART_OWNER|ENTIRE_OWNER|RENTAL|BORROWING)$", message = "must match pattern")
  String ownershipType;

  //quy trính sản xuất
  String productionProcess;

  //phương thức ghi chép
  String recordingMethod;

  //nguồn đầu vào
  String inputSource;

  //nguồn đầu ra
  String outputSource;

  //quy mô kinh doanh
  String businessScale;

  //Hàng tồn kho, phải thu, phải trả
  String inventoryReceivablePayable;

  //kỳ đánh giá
  @Pattern(regexp = "^(THREE_MONTHS|SIX_MONTHS|NINE_MONTHS|YEAR)$", message = "must match pattern")
  String evaluationPeriod;

  //doanh thu
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long revenue;

  //chi phí
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long cost;

  //lợi nhuận ghi nhận cho khách hàng
  @Min(0)
  @Max(Long.MAX_VALUE)
  Long profitsYourself;

  //đánh giá kết quả kinh doanh
  String businessPerformance;

  // vốn điều lệ
  Long charterCapital;

  //ngày thay đổi đăng ký kinh doanh gần nhất
  String registrationChangeDate;

  String payerId;
}
