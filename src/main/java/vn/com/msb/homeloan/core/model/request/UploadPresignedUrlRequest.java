package vn.com.msb.homeloan.core.model.request;

import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UploadPresignedUrlRequest {

  String product;

  String clientCode;

  String scopes;

  String documentType;

  @Schema(description = "Priority of uploading file")
  @NotNull
  Integer priority;

  @Schema(description = "File name to be stored")
  @NotBlank
  String filename;

  @Schema(description = "File prefix to be stored")
  @NotNull
  String path;
}

