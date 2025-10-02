package vn.com.msb.homeloan.core.model.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.model.UploadPresignedUrlData;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UploadPresignedUrlResponse {

  private UploadPresignedUrlData data;
  private String status;

}
