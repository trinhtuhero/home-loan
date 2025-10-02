package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCheckTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskCollateralTypeEnum;
import vn.com.msb.homeloan.core.constant.oprisk.OpRiskStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSOpRiskResponse {

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
