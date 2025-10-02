package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class MtkTracking {

  String loanId;
  String loanCode;
  String utmSource;
  String utmMedium;
  String utmCampaign;
  String utmContent;
  String adviseId;
}
