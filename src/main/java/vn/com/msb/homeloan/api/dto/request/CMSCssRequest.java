package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class CMSCssRequest {

  @NotBlank
  private Integer profileId;

  private String legalDocNo;

  @NotBlank
  private String loanApplicationId;
}
