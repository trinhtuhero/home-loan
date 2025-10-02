package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MvalueAssetInfo {

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
