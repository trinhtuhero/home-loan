package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.CardPriorityEnum;
import vn.com.msb.homeloan.core.constant.DebtDeductionRateEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CreditCard {

  String uuid;
  String loanId;
  // Thẻ chính/Thẻ phụ
  CardPriorityEnum cardPriority;

  // Loại thẻ
  String type;

  String typeName;

  // Hạn mức thẻ
  Long creditLimit;

  // Đối tượng cấp thẻ
  String object;

  String cardPolicyCode;
  String cardPolicyName;

  Integer timeLimit;

  // Trường tiểu học đầu tiên
  String firstPrimarySchool;

  // Đăng ký trích nợ tự động
  Boolean autoDebit;

  // Tỉ lệ trích nợ
  DebtDeductionRateEnum debtDeductionRate;

  // Số tài khoản trích nợ
  String debitAccountNumber;

  // Địa chỉ nhận thẻ
  String receivingAddress;

  // Tên in trên thẻ
  String nameInCard;

  String parentId;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    CreditCard that = (CreditCard) o;

    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(cardPriority, that.cardPriority)
        && HomeLoanUtil.compare(type, that.type)
        && HomeLoanUtil.compare(creditLimit, that.creditLimit)
        && HomeLoanUtil.compare(object, that.object)
        && HomeLoanUtil.compare(firstPrimarySchool, that.firstPrimarySchool)
        && HomeLoanUtil.compare(autoDebit, that.autoDebit)
        && HomeLoanUtil.compare(debtDeductionRate, that.debtDeductionRate)
        && HomeLoanUtil.compare(debitAccountNumber, that.debitAccountNumber)
        && HomeLoanUtil.compare(receivingAddress, that.receivingAddress)
        && HomeLoanUtil.compare(nameInCard, that.nameInCard);
  }
}
