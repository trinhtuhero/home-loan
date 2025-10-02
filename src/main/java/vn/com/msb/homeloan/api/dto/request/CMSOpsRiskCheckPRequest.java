package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
public class CMSOpsRiskCheckPRequest {

  @Valid
  private List<CheckPData> cmsOpsRiskRequests;

  private String loanApplicationId;

  @Data
  @Valid
  public static class CheckPData {

    @NotNull
    Set<@NotBlank String> identityCards;

    private String name;

    private String birthday;

    private String endDate;
  }
}
