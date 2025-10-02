package vn.com.msb.homeloan.api.dto.response;

import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.model.AssetEvaluateItem;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSAssetEvaluateResponse {

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
