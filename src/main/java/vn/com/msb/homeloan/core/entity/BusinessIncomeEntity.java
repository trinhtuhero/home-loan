package vn.com.msb.homeloan.core.entity;

import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.EvaluationPeriodEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.OwnershipTypeEnum;

@Data
@Entity
@Table(name = "business_incomes", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BusinessIncomeEntity extends BaseEntity {

  // loan_application_id
  @Column(name = "loan_application_id")
  String loanApplicationId;

  // owner_type
  // enum [Tôi, Vợ/Chồng, Người đồng trả nợ]
  @Enumerated(EnumType.STRING)
  @Column(name = "owner_type")
  OwnerTypeEnum ownerType;

  // business_type
  // enum [Hộ kinh doanh có ĐKKD, Doanh nghiệp, Hộ kinh doanh không có ĐKKD]
  @Enumerated(EnumType.STRING)
  @Column(name = "business_type")
  BusinessTypeEnum businessType;

  // business_line
  @Enumerated(EnumType.STRING)
  @Column(name = "business_line")
  BusinessLineEnum businessLine;

  //ngành nghề kinh doanh
  @Column(name = "business_activity")
  String businessActivity;

  // business_code
  @Column(name = "business_code")
  String businessCode;

  @Column(name = "issued_date")
  Date issuedDate;

  @Column(name = "place_of_issue")
  String placeOfIssue;

  @Column(name = "business_start_date")
  Date businessStartDate;

  // name
  @Column(name = "name")
  String name;

  // province
  @Column(name = "province")
  String province;

  // province_name
  @Column(name = "province_name")
  String provinceName;

  // district
  @Column(name = "district")
  String district;

  // district_name
  @Column(name = "district_name")
  String districtName;

  // ward
  @Column(name = "ward")
  String ward;

  // ward_name
  @Column(name = "ward_name")
  String wardName;

  // address
  @Column(name = "address")
  String address;

  // phone
  @Column(name = "phone")
  String phone;

  // value
  @Column(name = "value")
  Long value;

  //tỷ lệ vốn góp của khách hàng
  @Column(name = "capital_ratio")
  BigDecimal capitalRatio;

  //hình thức sở hữu
  @Enumerated(EnumType.STRING)
  @Column(name = "ownership_type")
  OwnershipTypeEnum ownershipType;

  //quy trính sản xuất
  @Column(name = "production_process")
  String productionProcess;

  //phương thức ghi chép
  @Column(name = "recording_method")
  String recordingMethod;

  //nguồn đầu vào
  @Column(name = "input_source")
  String inputSource;

  //nguồn đầu ra
  @Column(name = "output_source")
  String outputSource;

  //quy mô kinh doanh
  @Column(name = "business_scale")
  String businessScale;

  //Hàng tồn kho, phải thu, phải trả
  @Column(name = "inventory_receivable_payable")
  String inventoryReceivablePayable;

  //kỳ đánh giá
  @Enumerated(EnumType.STRING)
  @Column(name = "evaluation_period")
  EvaluationPeriodEnum evaluationPeriod;

  //doanh thu
  @Column(name = "revenue")
  Long revenue;

  //chi phí
  @Column(name = "cost")
  Long cost;

  //lợi nhuận
  @Column(name = "profit")
  Long profit;

  //biên lợi nhuận
  @Column(name = "profit_margin")
  BigDecimal profitMargin;

  //lợi nhuận ghi nhận cho khách hàng
  @Column(name = "profits_yourself")
  Long profitsYourself;

  //đánh giá kết quả kinh doanh
  @Column(name = "business_performance")
  String businessPerformance;

  // vốn điều lệ
  @Column(name = "charter_capital")
  Long charterCapital;

  //ngày thay đổi đăng ký kinh doanh gần nhất
  @Column(name = "registration_change_date")
  Date registrationChangeDate;

  @Column(name = "payer_id")
  String payerId;
}
