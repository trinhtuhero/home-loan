package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import vn.com.msb.homeloan.core.constant.ReceiveChannelEnum;
import vn.com.msb.homeloan.core.model.LoanAdviseCustomer;
import vn.com.msb.homeloan.core.model.LoanJourneyStatus;

@Slf4j
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LdpSearchResponse {

  String uuid;
  String fullName;
  String refCode;
  String status;
  String picRm;
  ReceiveChannelEnum receiveChannel;
  String loanCode;
  Date submitLoanRequestTime;
  List<LoanJourneyStatus> loanJourneyStatuses;
  List<LoanApplicationItemResponse> loanApplicationItems;
  CmsUserResponse cmsUser;
  LoanAdviseCustomer loanAdviseCustomer;
}
