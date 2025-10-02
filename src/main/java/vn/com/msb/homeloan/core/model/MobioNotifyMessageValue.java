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
public class MobioNotifyMessageValue {

  String dealId;
  String dealCode;
  String lastUpdate;
  String dealName;
  String profileId;
  String saleProcessId;
  String assigneeId;
  String assigneeEmail;
  String dealState;
}
