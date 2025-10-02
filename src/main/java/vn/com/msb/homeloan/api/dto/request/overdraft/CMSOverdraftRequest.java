package vn.com.msb.homeloan.api.dto.request.overdraft;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.api.dto.request.LoanItemCollateralDistributionRequest;

@Getter
@Setter
@ToString
public class CMSOverdraftRequest extends OverdraftRequest {

  @NotNull(message = "formOfCredit mustn't be null")
  @Pattern(regexp = "^(UNSECURED_OVERDRAFT|SECURED_OVERDRAFT)$", message = "must match pattern UNSECURED_OVERDRAFT or SECURED_OVERDRAFT")
  private String formOfCredit;

  @Max(value = 12)
  @NotNull(message = "loanTime mustn't be null")
  @Min(value = 1)
  private Integer loanTime;
  @NotNull(message = "interestCode mustn't be null")
  private String interestCode;
  private Float margin;
  @NotNull(message = "paymentAccountNumber mustn't be null")
  @NotBlank(message = "paymentAccountNumber mustn't be blank")
  private String paymentAccountNumber;
  private String debtPaymentMethod;

  private String loanAssetValue;

  private String interestRateProgram;
  private String documentNumber2;

  @Pattern(regexp = "^(VAY_THAU_CHI_TSBĐ_ONLINE)$", message = "must match pattern VAY_THAU_CHI_TSBĐ_ONLINE")
  private String productTextCode;

  @Valid
  List<LoanItemCollateralDistributionRequest> loanItemCollateralDistributions;
}
