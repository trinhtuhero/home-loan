package vn.com.msb.homeloan.api.dto.response.loanApplication;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoanApplicationResponse {

  String uuid;
  String profileId;
  String phone;
  String fullName;
  String gender;
  String nationality;
  String nationalityName;
  String email;
  String idNo;
  String issuedOn;
  String birthday;
  String placeOfIssue;
  String placeOfIssueName;
  String oldIdNo;
  String oldIdNo2;
  String oldIdNo3;
  String province;
  String provinceName;
  String district;
  String districtName;
  String ward;
  String wardName;
  String address;
  String maritalStatus;
  Integer numberOfDependents;
  String refCode;
  String currentStep;
  String picRm;
  Date receiveDate;
  String receiveChannel;
  String loanProduct;
  String status;
  String loanCode;
  Long totalIncome;
  Long totalIncomeExchange;
  String residenceProvince;
  String residenceProvinceName;
  String residenceDistrict;
  String residenceDistrictName;
  String residenceWard;
  String residenceWardName;
  String residenceAddress;

  String cifNo;
  String education;
  String partnerCode;
  String customerSegment;

  // cj4_interested
  String cj4Interested;
  // cj4_interested_date
  String cj4InterestedDate;

  String cj4CmsInterested;
}
