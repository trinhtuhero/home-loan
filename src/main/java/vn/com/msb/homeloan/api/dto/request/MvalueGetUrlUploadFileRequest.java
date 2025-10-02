package vn.com.msb.homeloan.api.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MvalueGetUrlUploadFileRequest {

  @NotBlank
  String loanId;

  @NotNull
  long documentMvalueId;

  @NotBlank
  String collateralId;

  @NotNull
  List<String> files;

  @NotBlank
  String collateralType;
}
