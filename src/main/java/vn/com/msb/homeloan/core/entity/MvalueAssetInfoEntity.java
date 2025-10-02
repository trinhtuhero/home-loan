package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "m_value_asset_info", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class MvalueAssetInfoEntity extends BaseEntity {

  @Column(name = "m_value_id")
  String mvalueId;

  @Column(name = "asset_id_m_value")
  Long assetIdMvalue;

  @Column(name = "valuation_notice")
  String valuationNotice;

  @Column(name = "valuation")
  Long valuation;

  @Column(name = "ownership_certificate_no")
  String ownershipCertificateNo;

  @Column(name = "issued_date")
  Date issuedDate;

  @Column(name = "issued_by")
  String issuedBy;

  @Column(name = "province_id")
  Long provinceId;

  @Column(name = "district_id")
  Long districtId;

  @Column(name = "ward_id")
  Long wardId;

  @Column(name = "street_name")
  String streetName;

  @Column(name = "address")
  String address;

  @Column(name = "file_link")
  String fileLink;

  @Column(name = "valuer")
  String valuer;

  @Column(name = "valuation_date")
  Date valuationDate;

  @Column(name = "status")
  String status;
}
