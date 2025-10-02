package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CMSGetOpRiskCollateralInfo {

  CollateralTypeEnum collateralType;

  CollateralStatusEnum collateralStatus;

  String mvalueId;

  String identifyInfo;

  OpRisk opRisk;

  String registrationOrContractNo;

  String savingBookNo;
}
