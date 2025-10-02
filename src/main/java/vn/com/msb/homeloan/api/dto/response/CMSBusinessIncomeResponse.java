package vn.com.msb.homeloan.api.dto.response;

import java.math.BigDecimal;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.BusinessLineEnum;
import vn.com.msb.homeloan.core.constant.BusinessTypeEnum;
import vn.com.msb.homeloan.core.constant.EvaluationPeriodEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.constant.OwnershipTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSBusinessIncomeResponse {

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

  String value;

  //ngành nghề kinh doanh
  String businessActivity;

  String issuedDate;

  String placeOfIssue;

  String businessStartDate;

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

  Long charterCapital;

  //ngày thay đổi đăng ký kinh doanh gần nhất
  String registrationChangeDate;

  String payerId;
}
