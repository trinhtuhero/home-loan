package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UploadPresignedUrlData {

  private String url;

  @JsonProperty("headersNeedToAdd")
  private Map<String, String> headersNeedToAdd;
}
