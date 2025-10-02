package vn.com.msb.homeloan.core.model;

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
public class CmsFeedbackReport {

  String uuid;
  String loanCode;
  String branchCode;
  String branchName;
  String loanPurpose;
  String fullName;
  String phone;
  String idNo;
  Long loanAmount;
  String picRmFullName;
  String picRmEmail;
  Integer rate;
  String additionalComment;
  String status;

  public CmsFeedbackReport(String uuid, String loanCode, String branchCode, String branchName,
      String loanPurpose, String fullName, String phone, String idNo, Long loanAmount,
      String picRmFullName, String picRmEmail, String additionalComment, Integer rate,
      String status) {
    this.uuid = uuid;
    this.loanCode = loanCode;
    this.branchCode = branchCode;
    this.branchName = branchName;
    this.loanPurpose = loanPurpose;
    this.fullName = fullName;
    this.phone = phone;
    this.idNo = idNo;
    this.loanAmount = loanAmount;
    this.picRmFullName = picRmFullName;
    this.picRmEmail = picRmEmail;
    this.rate = rate;
    this.additionalComment = additionalComment;
    this.status = status;
  }

  public CmsFeedbackReport(String loanCode, String branchCode, String branchName,
      String loanPurpose, String fullName, String idNo, Long loanAmount, String picRmFullName,
      String picRmEmail, String additionalComment, Integer rate, String status) {
    this.loanCode = loanCode;
    this.branchCode = branchCode;
    this.branchName = branchName;
    this.loanPurpose = loanPurpose;
    this.fullName = fullName;
    this.idNo = idNo;
    this.loanAmount = loanAmount;
    this.picRmFullName = picRmFullName;
    this.picRmEmail = picRmEmail;
    this.rate = rate;
    this.additionalComment = additionalComment;
    this.status = status;
  }
}
