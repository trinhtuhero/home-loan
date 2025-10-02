package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.FileInfo;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoanUploadFileRequest {

  @NotBlank
  String loanId;

  @NotBlank
  String fileConfigId;

  @NotEmpty
  List<FileInfo> files;
}
