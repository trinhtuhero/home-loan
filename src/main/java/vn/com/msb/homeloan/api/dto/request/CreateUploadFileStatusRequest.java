package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CreateUploadFileStatusRequest {

  @NotBlank
  String loanApplicationId;

  @NotBlank
  String fileConfigId;

  @NotNull
  @Min(0)
  @Max(1)
  Integer isEnough;
}
