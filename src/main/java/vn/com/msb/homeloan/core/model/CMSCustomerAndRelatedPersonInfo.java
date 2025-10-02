package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.entity.ContactPersonEntity;
import vn.com.msb.homeloan.core.entity.LoanApplicationEntity;
import vn.com.msb.homeloan.core.entity.LoanPayerEntity;
import vn.com.msb.homeloan.core.entity.MarriedPersonEntity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCustomerAndRelatedPersonInfo {

  LoanApplicationEntity customerInfo;
  MarriedPersonEntity marriedPerson;
  ContactPersonEntity contactPerson;
  List<LoanPayerEntity> loanPayers;
}
