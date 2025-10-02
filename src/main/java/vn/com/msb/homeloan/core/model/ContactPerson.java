package vn.com.msb.homeloan.core.model;

import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.constant.ContactPersonTypeEnum;
import vn.com.msb.homeloan.core.util.HomeLoanUtil;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPerson {

  String uuid;
  String loanId;
  ContactPersonTypeEnum type;
  String fullName;
  String phone;

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ContactPerson that = (ContactPerson) o;
    return (uuid != null && that.uuid != null && HomeLoanUtil.compare(uuid, that.uuid))
        && HomeLoanUtil.compare(type, that.type)
        && HomeLoanUtil.compare(fullName, that.fullName)
        && HomeLoanUtil.compare(phone, that.phone);
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }
}
