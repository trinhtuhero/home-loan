package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PartnerChannel {

  String uuid;
  String loanId;
  String dealAssignee;
  String dealReferenceCode;
  String dealReferralChannel;
}
