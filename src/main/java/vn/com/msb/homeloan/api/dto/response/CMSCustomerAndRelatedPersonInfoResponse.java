package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.com.msb.homeloan.core.model.ContactPerson;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSCustomerAndRelatedPersonInfoResponse {

  CMSCustomerInfoResponse customerInfo;
  CMSMarriedPersonResponse marriedPerson;
  ContactPerson contactPerson;
  List<CMSLoanPayerResponse> loanPayers;
}
