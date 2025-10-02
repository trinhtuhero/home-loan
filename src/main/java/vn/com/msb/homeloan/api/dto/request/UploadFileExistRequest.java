package vn.com.msb.homeloan.api.dto.request;

import lombok.*;
import vn.com.msb.homeloan.core.model.FileUploadExist;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileExistRequest {

  @NotBlank
  String loanId;

  @NotNull
  long documentMvalueId;

  @NotBlank
  String collateralId;

  @NotBlank
  String collateralType;

  @NotNull
  List<FileUploadExist> files;
}
