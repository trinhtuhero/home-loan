package vn.com.msb.homeloan.api.dto.request;

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
public class MtkTrackingRequest {

  String loanId;

  String utmSource;

  String utmMedium;

  String utmCampaign;

  String utmContent;

  String adviseId;
}
