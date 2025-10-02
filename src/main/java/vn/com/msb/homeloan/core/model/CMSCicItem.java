package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;
import vn.com.msb.homeloan.core.constant.cic.CicQueryStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSCicItem {

  String cicId;

  String cicCode;

  String identityCard;

  CicGroupEnum typeDebt;

  CicGroupEnum typeDebt12;

  CicGroupEnum typeDebt24;

  Boolean pass;

  CicQueryStatusEnum description;

  Long amountLoanNonMsbSecure;

  Long amountLoanNonMsbUnsecure;

  Long amountLoanMsbSecure;

  Long amountLoanMsbUnsecure;

  Long amountCardNonMsb;

  Long amountCardMsb;

  Long amountOverDraftMsb;

  String metaData;

  Boolean active;

  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Saigon")
  Date checkDate;

  String cicContent;
}
