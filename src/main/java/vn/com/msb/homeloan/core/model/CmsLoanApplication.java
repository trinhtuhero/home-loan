package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.util.DateUtils;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CmsLoanApplication {

  String uuid;
  String loanCode;
  String fullName;
  String phone;
  String idNo;
  String loanPurpose;
  String picRmEmail;
  String branchCode;
  String branchName;
  String status;
  String submittedDate;
  String receiveDate;
  String note;
  String link;
  Long loanAmount;

  String adviseDate;

  String adviseTimeFrame;

  String adviseStatus;

  public CmsLoanApplication(String uuid, String loanCode, String fullName, String phone,
      String idNo, String loanPurpose, Long loanAmount, String picRmEmail, String branchCode,
      String branchName, String status, Date submittedDate, Date receiveDate) {
    this.uuid = uuid;
    this.loanCode = loanCode;
    this.fullName = fullName;
    this.phone = phone;
    this.idNo = idNo;
    this.loanPurpose = loanPurpose;
    this.loanAmount = loanAmount;
    this.picRmEmail = picRmEmail;
    this.branchCode = branchCode;
    this.branchName = branchName;
    this.status = status;
    this.submittedDate = submittedDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(submittedDate),
            "yyyy-MM-dd hh:mm:ss");
    this.receiveDate = receiveDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(receiveDate),
            "yyyy-MM-dd hh:mm:ss");
  }

  public CmsLoanApplication(String uuid, String loanCode, String fullName, String phone,
      String idNo, String loanPurpose, Long loanAmount, String picRmEmail, String branchCode,
      String branchName, String status, Date submittedDate, Date receiveDate, String note,
      String link,
      Date adviseDate, String adviseTimeFrame, String adviseStatus) {
    this.uuid = uuid;
    this.loanCode = loanCode;
    this.fullName = fullName;
    this.loanAmount = loanAmount;
    this.phone = phone;
    this.idNo = idNo;
    this.loanPurpose = loanPurpose;
    this.picRmEmail = picRmEmail;
    this.branchCode = branchCode;
    this.branchName = branchName;
    this.status = status;
    this.receiveDate = receiveDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(receiveDate),
            "yyyy-MM-dd hh:mm:ss");
    this.submittedDate = submittedDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(submittedDate),
            "yyyy-MM-dd hh:mm:ss");
    this.note = note;
    this.link = link;
    this.adviseDate = adviseDate == null ? null
        : DateUtils.convertToSimpleFormat(DateUtils.convertUTCDate(adviseDate),
            "yyyy-MM-dd hh:mm:ss");
    this.adviseTimeFrame = adviseTimeFrame;
    this.adviseStatus = adviseStatus;
  }
}
