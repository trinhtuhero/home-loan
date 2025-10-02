package vn.com.msb.homeloan.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MvalueUploadFileUpdateStatusRequest {

  @NotNull
  long id;

  @NotBlank
  @Pattern(regexp = "^(UPLOADING|UPLOADED)$")
  String status;
}
