package vn.com.msb.homeloan.api.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DownloadPresignedUrlRequest {

  private String product;

  private String clientCode;

  private String scopes;

  @Schema(description = "File path to be downloaded")
  @NotBlank
  private String path;

}
