package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CreateUploadFileCommentRequest {

  @NotBlank
  String loanApplicationId;

  @NotBlank
  String fileConfigId;

  @NotBlank
  String comment;
}
