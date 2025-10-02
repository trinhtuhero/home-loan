package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import vn.com.msb.homeloan.core.constant.AdviseTimeFrameEnum;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoanAdviseCustomer {

  String uuid;
  // loan_application_id
  String loanApplicationId;
  // advise_date
  Date adviseDate;
  // advise_time_frame
  AdviseTimeFrameEnum adviseTimeFrame;
  // content
  String content;

  String status;
}
