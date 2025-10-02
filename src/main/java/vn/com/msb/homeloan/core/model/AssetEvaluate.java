package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AssetEvaluate {

  String uuid;
  String loanApplicationId;
  OwnerTypeEnum ownerType;
  String payerId;
  Long totalValue;
  Long debitBalance;
  Long incomeValue;
  Long rmInputValue;
  String rmReview;
  List<AssetEvaluateItem> assetEvaluateItems;
}
