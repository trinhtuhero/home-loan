package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSAssetEvaluateItemRequest {

  String uuid;

  String assetEvalId;

  @NotBlank
  @Pattern(regexp = "^(REAL_ESTATE|CAR|VALUABLE_PAPERS|OTHER)$", message = "must match pattern")
  String assetType;

  String assetTypeOther;

  @NotBlank
  String legalRecord;

  @NotNull
  Long value;

  @NotBlank
  String assetDescription;
}
