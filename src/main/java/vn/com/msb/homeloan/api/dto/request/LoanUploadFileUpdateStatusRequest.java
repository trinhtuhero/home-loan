package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanUploadFileUpdateStatusRequest {

  @NotBlank
  String loanUploadFileId;

  @NotBlank
  @Pattern(regexp = "^(UPLOADING|UPLOADED)$")
  String status;
}
