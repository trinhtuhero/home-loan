package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import javax.persistence.Column;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;
import vn.com.msb.homeloan.core.constant.IncomeFromEnum;
import vn.com.msb.homeloan.core.constant.OwnerTypeEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@SuperBuilder
@ToString
public class OtherIncome {

  String uuid;

  String loanApplicationId;

  OwnerTypeEnum ownerType;

  IncomeFromEnum incomeFrom;

  Long value;

  String rmReview;

  @JsonIgnore
  Instant createdAt;

  @JsonIgnore
  Instant updatedAt;

  String payerId;

  String payerName;

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
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    OtherIncome that = (OtherIncome) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(ownerType, that.ownerType)
        && HomeLoanUtil.compare(incomeFrom, that.incomeFrom)
        && HomeLoanUtil.compare(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
