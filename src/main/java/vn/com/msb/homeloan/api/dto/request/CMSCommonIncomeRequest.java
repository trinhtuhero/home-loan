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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CMSCommonIncomeRequest {

  @NotBlank
  String loanApplicationId;

  //luồng phê duyệt
  @NotBlank
  @Pattern(regexp = "^(FAST|NORMALLY|PRIORITY)$", message = "must match pattern")
  String approvalFlow;

  //phương pháp ghi nhận thu nhập 1
  @NotBlank
  @Pattern(regexp = "^(ACTUALLY_RECEIVED|EXCHANGE|DECLARATION)$", message = "must match pattern")
  String recognitionMethod1;

  //phương pháp ghi nhận thu nhập 2
  @Pattern(regexp = "^(NON_CREDIT_TRANSACTION|CREDIT_TRANSACTION|CREDIT_CARD_TRANSACTION|SAVING|ADVANTAGE_OF_ASSET_MINING|ACCUMULATED_ASSETS|OWNERSHIP_RESOURCES|RESOURCES_OWNED_AT_STRATEGIC_PARTNER|BASED_ON_COST_OF_LIVING)$", message = "must match pattern")
  String recognitionMethod2;

  // Chọn nguồn thu
  String[] selectedIncomes;
}
