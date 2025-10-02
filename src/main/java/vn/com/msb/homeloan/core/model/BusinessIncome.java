package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.EvaluationPeriodEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.OwnershipTypeEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
public class BusinessIncome {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  BusinessTypeEnum businessType;

  BusinessLineEnum businessLine;

  String businessCode;

  String name;

  String province;

  String provinceName;

  String district;

  String districtName;

  String ward;

  String wardName;

  String address;

  String phone;

  Long value;

  @JsonIgnore
  Instant createdAt;

  @JsonIgnore
  Instant updatedAt;

  //ngành nghề kinh doanh
  String businessActivity;

  Date issuedDate;

  String placeOfIssue;

  Date businessStartDate;

  BigDecimal capitalRatio;

  //hình thức sở hữu
  OwnershipTypeEnum ownershipType;

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
  EvaluationPeriodEnum evaluationPeriod;

  //doanh thu
  Long revenue;

  //chi phí
  Long cost;

  //lợi nhuận
  Long profit;

  //biên lợi nhuận
  BigDecimal profitMargin;

  //lợi nhuận ghi nhận cho khách hàng
  Long profitsYourself;

  //đánh giá kết quả kinh doanh
  String businessPerformance;

  // vốn điều lệ
  Long charterCapital;

  //ngày thay đổi đăng ký kinh doanh gần nhất
  Date registrationChangeDate;

  String payerId;

  String payerName;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BusinessIncome that = (BusinessIncome) o;

    // điều kiện với trg business_type nếu business_type = doanh nghiệp/ hộ cá nhân có đăng ký kinh doanh thì required
    boolean condition1 = false;
    if (BusinessTypeEnum.ENTERPRISE.equals(businessType)
        || BusinessTypeEnum.WITH_REGISTRATION.equals(businessType)) {
      condition1 = true;
    }

    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(ownerType, that.ownerType)
        && HomeLoanUtil.compare(businessType, that.businessType)
        && HomeLoanUtil.compare(businessLine, that.businessLine)
        && (!condition1 || HomeLoanUtil.compare(businessCode, that.businessCode))
        && HomeLoanUtil.compare(name, that.name)
        && HomeLoanUtil.compare(province, that.province)
        && HomeLoanUtil.compare(provinceName, that.provinceName)
        && HomeLoanUtil.compare(district, that.district)
        && HomeLoanUtil.compare(districtName, that.districtName)
        && HomeLoanUtil.compare(ward, that.ward)
        && HomeLoanUtil.compare(wardName, that.wardName)
        && HomeLoanUtil.compare(address, that.address)
        && HomeLoanUtil.compare(phone, that.phone)
        && HomeLoanUtil.compare(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}


