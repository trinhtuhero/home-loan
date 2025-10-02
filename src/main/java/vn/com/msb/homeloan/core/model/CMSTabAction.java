package vn.com.msb.homeloan.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class CMSTabAction {

  String uuid;

  String loanId;

  String emplId;

  String tabCode;

  String status;
}
