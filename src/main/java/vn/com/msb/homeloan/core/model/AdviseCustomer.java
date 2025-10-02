package vn.com.msb.homeloan.core.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.core.constant.AdviseStatusEnum;
import vn.com.msb.homeloan.core.constant.LoanPurposeEnum;

@Getter
@Setter
@ToString
public class AdviseCustomer {

  private String uuid;

  private String customerName;

  private String customerPhone;

  private String product;

  private String province;

  private AdviseStatusEnum status;
}
