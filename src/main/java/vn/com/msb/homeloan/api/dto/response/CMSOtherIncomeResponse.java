package vn.com.msb.homeloan.api.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CMSOtherIncomeResponse {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  IncomeFromEnum incomeFrom;

  Long value;

  String rmReview;

  String payerId;

  //Địa chỉ tài sản/Loại xe
  String rentProperty;

  //Bên thuê
  String tenant;

  //Số điện thoại Bên thuê
  String tenantPhone;

  //Mục đích thuê
  String tenantPurpose;

  //Giá cho thuê
  Long tenantPrice;

  BigDecimal incomePerMonth;

  Integer quantity;

  List<LandTransactionRequest> landTransactions;
}
