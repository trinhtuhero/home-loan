package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class CMSOpsRiskCheckCRequest {

  @Valid
  private List<CheckCData> cmsOpsRiskRequests;

  private String loanApplicationId;

  @Data
  @Valid
  public static class CheckCData {

    @NotBlank
    @Pattern(regexp = "[1-3]", message = "must match pattern")
    private String collateralType;

    @NotNull
    private Set<@NotBlank String> identifiesInfoSet;
  }
}
