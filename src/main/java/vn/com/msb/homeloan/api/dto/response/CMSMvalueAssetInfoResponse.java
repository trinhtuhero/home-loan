package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSMvalueAssetInfoResponse {

  String uuid;

  String mvalueId;

  Long assetIdMvalue;

  String valuationNotice;

  Long valuation;

  String ownershipCertificateNo;

  @JsonFormat(pattern = "yyyyMMddHHmmss")
  Date issuedDate;

  String issuedBy;

  Long provinceId;

  Long districtId;

  Long wardId;

  String streetName;

  String address;

  String fileLink;

  String valuer;

  @JsonFormat(pattern = "yyyyMMddHHmmss")
  Date valuationDate;

  String status;
}
