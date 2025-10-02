package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSMvalueResponse {

  String uuid;

  String loanApplicationId;

  String assetCode;

  @JsonFormat(pattern = "yyyyMMddHHmmss", timezone = "Asia/Saigon")
  Date checkDate;

  String message;

  List<CMSMvalueAssetInfoResponse> assetInfo;
}
