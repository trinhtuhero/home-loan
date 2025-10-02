package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.ProductTextCodeEnum;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class LoanItemOverDraftDto {

  String loanApplicationId;
  ProductTextCodeEnum productTextCode;
  List<LoanItemCollateralDistribution> loanItemCollateralDistributions;
}
