package vn.com.msb.homeloan.api.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSAssetEvaluateRequest {

  String uuid;

  @NotBlank
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(ME|MARRIED_PERSON|PAYER_PERSON)$", message = "must match pattern")
  String ownerType;

  String payerId;

  @NotNull
  Long totalValue;

  @NotNull
  Long debitBalance;

  @NotNull
  Long incomeValue;

  @NotNull
  Long rmInputValue;

  String rmReview;

  @Valid
  @NotNull
  List<CMSAssetEvaluateItemRequest> assetEvaluateItems;
}
