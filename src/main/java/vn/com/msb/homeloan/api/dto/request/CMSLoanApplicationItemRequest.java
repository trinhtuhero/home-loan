package vn.com.msb.homeloan.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class CMSLoanApplicationItemRequest {

  String uuid;

  @NotNull
  String loanApplicationId;

  @NotBlank
  @Pattern(regexp = "^(DEBT_PAYMENT_1|DEBT_PAYMENT_2|DEBT_PAYMENT_3)$", message = "must match pattern")
  String debtPaymentMethod;

  Integer gracePeriod;

  @NotBlank
  @Pattern(regexp = "^(PROGRAM_INTEREST_RATE_1|PROGRAM_INTEREST_RATE_2|PROGRAM_INTEREST_RATE_3)$", message = "must match pattern")
  String interestRateProgram;

  @NotBlank
  @Pattern(regexp = "^(LAND|M_HOUSING|XAY_SUA_NHA|TIEU_DUNG_TSBĐ|HOAN_THIEN_CHCC|VAY_KINH_DOANH_HAN_MUC|VAY_KINH_DOANH_TUNG_LAN)$", message = "must match pattern")
  String productTextCode;

  @NotBlank
  String disbursementMethod;

  String disbursementMethodOther;

  String rmReview;

  @NotBlank
  String interestCode;

  String documentNumber2;

  @NotBlank
  @Pattern(regexp = "^(LAND|TIEU_DUNG|XAY_SUA_NHA|HO_KINH_DOANH)$", message = "must match LAND|TIEU_DUNG|XAY_SUA_NHA|HO_KINH_DOANH")
  String loanPurpose;

  @NotNull
  @Min(3)
  @Max(420)
  Integer loanTime;

  @NotNull
  @Min(1)
  Long loanAssetValue;

  @NotNull
  @Min(1)
  Long loanAmount;

  String beneficiaryBank;

  @Pattern(regexp = "^$|^[A-Za-z0-9]+$", message = "should not contain any special characters")
  String beneficiaryAccount;

  String beneficiaryFullName;

  // Mục đích vay chi tiết
  @Pattern(regexp = "^(VAY_BO_SUNG_VON_KINH_DOANH|VAY_DAU_TU_TAI_SAN_CO_DINH)$")
  String loanPurposeDetail;

  // Vay bổ sung vốn kinh doanh
  @Pattern(regexp = "^(VAY_NGAN_HAN|VAY_HAN_MUC|VAY_TRUNG_DAI_HAN)$")
  String loanSupplementingBusinessCapital;

  // Vay đầu tư tài sản cố định
  @Pattern(regexp = "^(VAY_MUA_BĐS_CO_SAN|VAY_HOAN_VON_MUA_BĐS|VAY_MUA_BĐS_QUA_DAU_GIA_PHAT_MAI|MUA_PTVT_MMTB_TSCĐ_PHUC_VU_KD|XD_SUA_CHUA_NHA_XUONG_BĐS_KD|VAY_HOAN_VON_XD_SUA_CHUA_NHA_XUONG_BĐS_KD)$")
  String loanInvestmentFixedAsset;

  // Thời gian khế ước nhận nợ
  @Pattern(regexp = "^(?:[1-9]\\d+|[1-9])$", message = "must greater than 1")
  String debtAcknowledgmentContractPeriod;

  // Là chủ doanh nghiệp tư nhân
  Boolean isPrivateBusinessOwner;

  // Khoản vay tái cấp
  @Pattern(regexp = "^(B_SCORE|NON_B_SCORE)$")
  String refinanceLoan;

  @NotEmpty
  @Valid
  List<LoanItemCollateralDistributionRequest> loanItemCollateralDistributions;
}
