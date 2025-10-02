package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.CollateralStatusEnum;
import vn.com.msb.homeloan.core.constant.CollateralTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSGetOpRiskCollateralInfoResponse {

  CollateralTypeEnum collateralType;

  CollateralStatusEnum collateralStatus;

  String mvalueId;

  String identifyInfo;

  CMSOpRiskResponse opRisk;

  String registrationOrContractNo;

  String savingBookNo;
}
