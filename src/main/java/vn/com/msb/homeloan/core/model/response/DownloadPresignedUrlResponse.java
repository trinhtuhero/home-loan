package vn.com.msb.homeloan.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import vn.com.msb.homeloan.core.model.UploadPresignedUrlData;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DownloadPresignedUrlResponse {

  private UploadPresignedUrlData data;
  private String status;
}
