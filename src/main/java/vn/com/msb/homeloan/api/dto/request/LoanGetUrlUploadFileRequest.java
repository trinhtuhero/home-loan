package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class LoanGetUrlUploadFileRequest {

  @NotBlank
  String loanId;

  @NotBlank
  String fileConfigId;

  @NotNull
  List<String> files;
}
