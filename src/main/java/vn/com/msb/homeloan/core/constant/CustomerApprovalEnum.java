package vn.com.msb.homeloan.core.constant;

import lombok.Getter;

@Getter
public enum CustomerApprovalEnum {

  APPROVAL("APPROVAL"),

  REJECT("REJECT");

  private final String code;

  CustomerApprovalEnum(String code) {
    this.code = code;
  }

}
