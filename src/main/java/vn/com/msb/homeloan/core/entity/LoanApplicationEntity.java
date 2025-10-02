package vn.com.msb.homeloan.core.entity;

import java.time.Instant;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.ColumnResult;
import javax.persistence.ConstructorResult;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.SqlResultSetMappings;
import javax.persistence.Table;
import javax.persistence.Transient;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CustomerSegmentEnum;
import vn.com.msb.homeloan.core.constant.EducationEnum;
import vn.com.msb.homeloan.core.constant.GenderEnum;
import vn.com.msb.homeloan.core.constant.InterestedEnum;
import vn.com.msb.homeloan.core.constant.LoanInfoStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanProductEnum;
import vn.com.msb.homeloan.core.constant.MaritalStatusEnum;
import vn.com.msb.homeloan.core.constant.MobioStatusEnum;
import vn.com.msb.homeloan.core.constant.NationalEnum;
import vn.com.msb.homeloan.core.constant.ReceiveChannelEnum;
import vn.com.msb.homeloan.core.model.CmsFeedbackReport;
import vn.com.msb.homeloan.core.model.CmsLoanApplication;
import vn.com.msb.homeloan.core.model.CmsTATReport;
import vn.com.msb.homeloan.core.model.LoanApplication;

@Data
@Entity
@Table(name = "loan_applications", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@FieldDefaults(level = AccessLevel.PRIVATE)
@SqlResultSetMappings(
    {@SqlResultSetMapping(name = "CmsSearchResult", classes = {
        @ConstructorResult(targetClass = CmsLoanApplication.class, columns = {
            @ColumnResult(name = "uuid", type = String.class),
            @ColumnResult(name = "loanCode", type = String.class),
            @ColumnResult(name = "fullName", type = String.class),
            @ColumnResult(name = "phone", type = String.class),
            @ColumnResult(name = "idNo", type = String.class),
            @ColumnResult(name = "loanPurpose", type = String.class),
            @ColumnResult(name = "loanAmount", type = Long.class),
            @ColumnResult(name = "picRmEmail", type = String.class),
            @ColumnResult(name = "branchCode", type = String.class),
            @ColumnResult(name = "branchName", type = String.class),
            @ColumnResult(name = "status", type = String.class),
            @ColumnResult(name = "submittedDate", type = Date.class),
            @ColumnResult(name = "receiveDate", type = Date.class),
            @ColumnResult(name = "note", type = String.class),
            @ColumnResult(name = "link", type = String.class),
            @ColumnResult(name = "adviseDate", type = Date.class),
            @ColumnResult(name = "adviseTimeFrame", type = String.class),
            @ColumnResult(name = "adviseStatus", type = String.class)
        })}),
        @SqlResultSetMapping(name = "CmsExport", classes = {
            @ConstructorResult(targetClass = CmsLoanApplication.class, columns = {
                @ColumnResult(name = "uuid", type = String.class),
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "phone", type = String.class),
                @ColumnResult(name = "idNo", type = String.class),
                @ColumnResult(name = "loanPurpose", type = String.class),
                @ColumnResult(name = "loanAmount", type = Long.class),
                @ColumnResult(name = "picRmEmail", type = String.class),
                @ColumnResult(name = "branchCode", type = String.class),
                @ColumnResult(name = "branchName", type = String.class),
                @ColumnResult(name = "status", type = String.class),
                @ColumnResult(name = "submittedDate", type = Date.class),
                @ColumnResult(name = "receiveDate", type = Date.class)})
        }),

        @SqlResultSetMapping(name = "GetListLoanInfo", classes = {
            @ConstructorResult(targetClass = LoanApplication.class, columns = {
                @ColumnResult(name = "uuid", type = String.class),
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "status", type = String.class),
                @ColumnResult(name = "receiveChannel", type = String.class),
                @ColumnResult(name = "picRm", type = String.class),
                @ColumnResult(name = "refCode", type = String.class)})
        }),

        @SqlResultSetMapping(name = "CmsTATReport", classes = {
            @ConstructorResult(targetClass = CmsTATReport.class, columns = {
                @ColumnResult(name = "uuid", type = String.class),
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "branchCode", type = String.class),
                @ColumnResult(name = "branchName", type = String.class),
                @ColumnResult(name = "loanPurpose", type = String.class),
                @ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "phone", type = String.class),
                @ColumnResult(name = "idNo", type = String.class),
                @ColumnResult(name = "loanAmount", type = Long.class),
                @ColumnResult(name = "picRmFullName", type = String.class),
                @ColumnResult(name = "picRmEmail", type = String.class),
                @ColumnResult(name = "submittedDate", type = Date.class),
                @ColumnResult(name = "receiveDate", type = Date.class),
                @ColumnResult(name = "finishDate", type = Date.class),
                @ColumnResult(name = "status", type = String.class)})
        }),

        @SqlResultSetMapping(name = "CmsFeedbackReport", classes = {
            @ConstructorResult(targetClass = CmsFeedbackReport.class, columns = {
                @ColumnResult(name = "uuid", type = String.class),
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "branchCode", type = String.class),
                @ColumnResult(name = "branchName", type = String.class),
                @ColumnResult(name = "loanPurpose", type = String.class),
                @ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "phone", type = String.class),
                @ColumnResult(name = "idNo", type = String.class),
                @ColumnResult(name = "loanAmount", type = Long.class),
                @ColumnResult(name = "picRmFullName", type = String.class),
                @ColumnResult(name = "picRmEmail", type = String.class),
                @ColumnResult(name = "rate", type = Integer.class),
                @ColumnResult(name = "additionalComment", type = String.class),
                @ColumnResult(name = "status", type = String.class)})
        }),

        @SqlResultSetMapping(name = "CmsTATReportExport", classes = {
            @ConstructorResult(targetClass = CmsTATReport.class, columns = {
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "branchCode", type = String.class),
                @ColumnResult(name = "branchName", type = String.class),
                @ColumnResult(name = "loanPurpose", type = String.class),
                @ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "idNo", type = String.class),
                @ColumnResult(name = "loanAmount", type = Long.class),
                @ColumnResult(name = "picRmFullName", type = String.class),
                @ColumnResult(name = "picRmEmail", type = String.class),
                @ColumnResult(name = "submittedDate", type = Date.class),
                @ColumnResult(name = "receiveDate", type = Date.class),
                @ColumnResult(name = "finishDate", type = Date.class),
                @ColumnResult(name = "status", type = String.class)})
        }),

        @SqlResultSetMapping(name = "CmsFeedbackReportExport", classes = {
            @ConstructorResult(targetClass = CmsFeedbackReport.class, columns = {
                @ColumnResult(name = "loanCode", type = String.class),
                @ColumnResult(name = "branchCode", type = String.class),
                @ColumnResult(name = "branchName", type = String.class),
                @ColumnResult(name = "loanPurpose", type = String.class),
                @ColumnResult(name = "fullName", type = String.class),
                @ColumnResult(name = "idNo", type = String.class),
                @ColumnResult(name = "loanAmount", type = Long.class),
                @ColumnResult(name = "picRmFullName", type = String.class),
                @ColumnResult(name = "picRmEmail", type = String.class),
                @ColumnResult(name = "rate", type = Integer.class),
                @ColumnResult(name = "additionalComment", type = String.class),
                @ColumnResult(name = "status", type = String.class)})
        }),
    })
public class LoanApplicationEntity extends BaseEntity {

  @Column(name = "profile_id")
  String profileId;

  @Column(name = "phone")
  String phone;

  @Column(name = "full_name")
  String fullName;

  @Column(name = "age")
  Integer age;

  @Column(name = "gender")
  @Enumerated(EnumType.STRING)
  GenderEnum gender;

  @Column(name = "nationality")
  @Enumerated(EnumType.STRING)
  NationalEnum nationality;

  @Column(name = "email")
  String email;

  @Column(name = "id_no")
  String idNo;

  @Column(name = "issued_on")
  Date issuedOn;

  @Column(name = "birthday")
  Date birthday;

  @Column(name = "place_of_issue")
  String placeOfIssue;

  @Column(name = "old_id_no")
  String oldIdNo;

  @Column(name = "old_id_no_2")
  String oldIdNo2;

  @Column(name = "old_id_no_3")
  String oldIdNo3;

  @Column(name = "province")
  String province;

  @Column(name = "province_name")
  String provinceName;

  @Column(name = "district")
  String district;

  @Column(name = "district_name")
  String districtName;

  @Column(name = "ward")
  String ward;

  @Column(name = "ward_name")
  String wardName;

  @Column(name = "address")
  String address;

  @Column(name = "residence_province")
  String residenceProvince;

  @Column(name = "residence_province_name")
  String residenceProvinceName;

  @Column(name = "residence_district")
  String residenceDistrict;

  @Column(name = "residence_district_name")
  String residenceDistrictName;

  @Column(name = "residence_ward")
  String residenceWard;

  @Column(name = "residence_ward_name")
  String residenceWardName;

  @Column(name = "residence_address")
  String residenceAddress;

  @Enumerated(EnumType.STRING)
  @Column(name = "marital_status")
  private MaritalStatusEnum maritalStatus;

  @Column(name = "ref_code")
  String refCode;

  @Column(name = "number_of_dependents")
  Integer numberOfDependents;

  @Column(name = "status")
  @Enumerated(EnumType.STRING)
  LoanInfoStatusEnum status;

  @Column(name = "loan_code")
  String loanCode;

  @Column(name = "current_step")
  String currentStep;

  @Column(name = "download_status")
  Integer downloadStatus;

  @Column(name = "pic_rm")
  String picRm;

  @Column(name = "cif_no")
  String cifNo;

  @Column(name = "education")
  @Enumerated(EnumType.STRING)
  EducationEnum education;

  @Column(name = "partner_code")
  String partnerCode;

  @Column(name = "customer_segment")
  @Enumerated(EnumType.STRING)
  CustomerSegmentEnum customerSegment;

  @Column(name = "receive_channel")
  @Enumerated(EnumType.STRING)
  ReceiveChannelEnum receiveChannel;

  @Column(name = "receive_date")
  private Instant receiveDate;

  @Column(name = "loan_product")
  @Enumerated(EnumType.STRING)
  LoanProductEnum loanProduct;

  @Transient
  String placeOfIssueName;

  //mobio status
  @Column(name = "mobio_status")
  @Enumerated(EnumType.STRING)
  MobioStatusEnum mobioStatus;

  // cj4_interested
  @Column(name = "cj4_interested")
  @Enumerated(EnumType.STRING)
  InterestedEnum cj4Interested;

  // cj4_interested_date
  @Column(name = "cj4_interested_date")
  Date cj4InterestedDate;

  // is_customer_declined_to_confirm
  @Column(name = "is_customer_declined_to_confirm")
  Boolean isCustomerDeclinedToConfirm;

  // cj4_cms_interested
  @Column(name = "cj4_cms_interested")
  @Enumerated(EnumType.STRING)
  InterestedEnum cj4CmsInterested;

  // cj4_cms_interested_date
  @Column(name = "cj4_cms_interested_date")
  Date cj4CmsInterestedDate;

  // cj4_send_lead_status
  @Column(name = "cj4_send_lead_status")
  String cj4SendLeadStatus;

  // cj4_send_lead_date
  @Column(name = "cj4_send_lead_date")
  Date cj4SendLeadDate;
}
