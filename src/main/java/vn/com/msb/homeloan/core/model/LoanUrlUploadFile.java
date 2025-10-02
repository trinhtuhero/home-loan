package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoanUrlUploadFile {

  String loanUploadFileId;
  String fileName;
  String filePath;
  UploadPresignedUrlData urlData;
}
