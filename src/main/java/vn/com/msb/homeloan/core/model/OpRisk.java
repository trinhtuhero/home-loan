package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskStatusEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class OpRisk {

  String uuid;

  String loanApplicationId;

  String identityCard;

  String name;

  String birthday;

  String endDate;

  String metaData;

  Boolean pass;

  OpRiskStatusEnum status;

  OpRiskCheckTypeEnum checkType;

  OpRiskCollateralTypeEnum collateralType;

  String identifyInfo;

  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Saigon")
  Date checkDate;
}
