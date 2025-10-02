package vn.com.msb.homeloan.api.dto.response.overdraft;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OverdraftResponse {

  String uuid;

  String loanApplicationId;

  String overdraftPurpose;

  Long loanAmount;

  String overdraftSubject;
}
