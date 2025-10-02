package vn.com.msb.homeloan.core.migration.releases._302;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vn.com.msb.homeloan.core.constant.ApprovalFlowEnum;
import vn.com.msb.homeloan.core.constant.CustomerSegmentEnum;
import vn.com.msb.homeloan.core.constant.DebtPaymentMethodEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.LoanProductEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;
import vn.com.msb.homeloan.core.constant.ProgramInterestRateEnum;
import vn.com.msb.homeloan.core.constant.ReceiveChannelEnum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod1Enum;
import vn.com.msb.homeloan.core.constant.RecognitionMethod2Enum;
import vn.com.msb.homeloan.core.model.LoanJourneyStatus;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OldLoanApplication {

  String uuid;
  String profileId;
  String phone;
  String fullName;
  GenderEnum gender;
  String nationality;
  String email;
  String idNo;
  Date issuedOn;
  Date birthday;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  MaritalStatusEnum maritalStatus;
  Integer numberOfDependents;
  String refCode;
  LoanPurposeEnum loanPurpose;
  Long loanAssetValue;
  Long loanAmount;
  Integer loanTime;
  String status;
  String currentStep;
  Integer downloadStatus;
  String picRm;
  @JsonIgnore
  Instant receiveDate;
  ReceiveChannelEnum receiveChannel;
  LoanProductEnum loanProduct;
  String loanCode;
  Date submitLoanRequestTime;
  List<LoanJourneyStatus> loanJourneyStatuses;
  Long totalIncome;
  Long totalIncomeExchange;

  Integer age;
  String residenceProvince;
  String residenceProvinceName;
  String residenceDistrict;
  String residenceDistrictName;
  String residenceWard;
  String residenceWardName;
  String residenceAddress;
  String cifNo;
  EducationEnum education;
  String partnerCode;
  CustomerSegmentEnum customerSegment;

  //luồng phê duyệt
  ApprovalFlowEnum approvalFlow;

  //phương pháp ghi nhận thu nhập 1
  RecognitionMethod1Enum recognitionMethod1;

  //phương pháp ghi nhận thu nhập 2
  RecognitionMethod2Enum recognitionMethod2;

  // Chọn nguồn thu
  String[] selectedIncomes;

  @JsonIgnore
  Instant createdAt;
  @JsonIgnore
  Instant updatedAt;

  //phương thức trả nợ
  DebtPaymentMethodEnum debtPaymentMethod;

  //thời gian ân hạn gốc
  Integer gracePeriod;

  //Chương trình áp dụng lãi suất
  ProgramInterestRateEnum programInterestRates;

  //Mã văn bản sản phẩm
  ProductTextCodeEnum productTextCode;

  //Phương thức giải ngân
  String disbursementMethod;

  String rmReview;

  //interest_code(Mã lãi suất)
  String interestCode;

  //document_number_2(Mã văn bản sản phẩm 2)
  String documentNumber2;

  //officer_code(Mã officer code)
  String officerCode;
}
