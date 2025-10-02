package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.LoanUploadFileStatusEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanUploadFile {

  String uuid;
  String fileConfigId;
  String loanApplicationId;
  String fileName;
  String folder;
  LoanUploadFileStatusEnum status;

  FileConfig fileConfig;
}
