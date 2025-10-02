package vn.com.msb.homeloan.api.dto.response.advisecustomer;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LDPAdviseCustomerResponse {

  private String loanApplicationId;

  private String adviseDate;

  private String adviseTimeFrame;

  private String content;

  private String status;
}
