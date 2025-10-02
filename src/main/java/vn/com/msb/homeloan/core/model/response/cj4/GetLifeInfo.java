package vn.com.msb.homeloan.core.model.response.cj4;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategy.class)
public class GetLifeInfo {

  String transactionId;
  String loanId;
  String productName;
  String leadStatus;
  String orderStatus;
  String contractStatus;
  Integer isInterested;
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

  CalculateInsurance lifeCalculatorResponse;
}
