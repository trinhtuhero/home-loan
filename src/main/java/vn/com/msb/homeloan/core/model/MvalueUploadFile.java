package vn.com.msb.homeloan.core.model;

import lombok.*;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MvalueUploadFile {

  long id;
  String documentMvalueId;
  String loanApplicationId;
  String fileName;
  String folder;
  String status;

}
