package vn.com.msb.homeloan.api.dto.data.cj4;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetLifeInfoData {

  String transactionId;
  String loanId;
  String productName;
  String leadStatus;
  String orderStatus;
  String contractStatus;

  //  Integer isInterested;
  String interestedStatus;

  Long insuranceMoney;
  Long yearlyFee;
  Integer premiumTerm;
  String fullName;
  String phoneNumber;
  String email;
  String dob;
  Integer age;
  String gender;
  String provinceId;
  String branchId;
  String bsName;
  String bsPhoneNumber;
  String orderId;
  String contractNumber;
  String updateTransactionDate;
  String employeeId;
  String leadStatusName;
  String orderStatusName;
  String contractStatusName;

  CalculateInsuranceData lifeCalculatorResponse;
}
