package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.util.DateUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CmsTATReport {

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
  String submittedDate;
  String receiveDate;
  String finishDate;
  String status;

  public CmsTATReport(String uuid, String loanCode, String branchCode, String branchName,
      String loanPurpose, String fullName, String phone, String idNo, Long loanAmount,
      String picRmFullName, String picRmEmail, Date submittedDate, Date receiveDate,
      Date finishDate, String status) {
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
    this.receiveDate = receiveDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(receiveDate), "yyyyMMddHHmmss");
    this.submittedDate = submittedDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(submittedDate),
            "yyyyMMddHHmmss");
    this.finishDate = finishDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(finishDate), "yyyyMMddHHmmss");
    this.status = status;
  }

  public CmsTATReport(String loanCode, String branchCode, String branchName, String loanPurpose,
      String fullName, String idNo, Long loanAmount, String picRmFullName, String picRmEmail,
      Date submittedDate, Date receiveDate, Date finishDate, String status) {
    this.loanCode = loanCode;
    this.branchCode = branchCode;
    this.branchName = branchName;
    this.loanPurpose = loanPurpose;
    this.fullName = fullName;
    this.idNo = idNo;
    this.loanAmount = loanAmount;
    this.picRmFullName = picRmFullName;
    this.picRmEmail = picRmEmail;
    this.receiveDate = receiveDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(receiveDate),
            "dd/MM/yyyy HH:mm:ss");
    this.submittedDate = submittedDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(submittedDate),
            "dd/MM/yyyy HH:mm:ss");
    this.finishDate = finishDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(finishDate),
            "dd/MM/yyyy HH:mm:ss");
    this.status = status;
  }
}
