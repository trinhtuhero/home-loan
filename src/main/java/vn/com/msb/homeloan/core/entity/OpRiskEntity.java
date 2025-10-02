package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskStatusEnum;

@Data
@Entity
@Table(name = "op_risk", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OpRiskEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "identity_card")
  String identityCard;

  @Column(name = "name")
  String name;

  @Column(name = "birthday")
  Date birthday;

  @Column(name = "end_date")
  Date endDate;

  @Column(name = "meta_data")
  String metaData;

  @Column(name = "pass")
  Boolean pass;

  @Enumerated(EnumType.STRING)
  @Column(name = "status")
  OpRiskStatusEnum status;

  @Enumerated(EnumType.STRING)
  @Column(name = "check_type")
  OpRiskCheckTypeEnum checkType;

  @Enumerated(EnumType.STRING)
  @Column(name = "collateral_type")
  OpRiskCollateralTypeEnum collateralType;

  @Column(name = "identify_info")
  String identifyInfo;

  @Column(name = "active")
  Boolean active;

  @Column(name = "check_date")
  Date checkDate;
}
