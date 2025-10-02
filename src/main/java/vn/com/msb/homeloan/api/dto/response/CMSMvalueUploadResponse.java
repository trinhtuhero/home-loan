package vn.com.msb.homeloan.api.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSMvalueUploadResponse {
  long id;
  String loanApplicationId;
  String documentMvalueId;
  String mvalueCode;
  String fileName;
  String folder;
  String status;
  String mvalueStatus;
  String collateralId;
  String uploadUser;
  String collateralTypeECM;
  String loanUploadFileId;
}
