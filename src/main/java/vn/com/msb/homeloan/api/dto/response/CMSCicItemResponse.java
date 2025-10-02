package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.cic.CicGroupEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCicItemResponse {

  String cicId;

  String cicCode;

  String identityCard;

  CicGroupEnum typeDebt;

  CicGroupEnum typeDebt12;

  CicGroupEnum typeDebt24;

  Boolean pass;

  String description;

  Long amountLoanNonMsbSecure;

  Long amountLoanNonMsbUnsecure;

  Long amountLoanMsbSecure;

  Long amountLoanMsbUnsecure;

  Long amountCardNonMsb;

  Long amountCardMsb;

//    Long amountOverDraftMsb;

  String metaData;

  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Saigon")
  Date checkDate;
}
