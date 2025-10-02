package vn.com.msb.homeloan.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DataCommonResponse {

  private String uuid;
  private String token;
  private String message;
  private String phoneNumber;
  private Long transId;
  private String transactionId;
  private String redisKey;
  private String orderId;
  private Long totalPremium;
  private String effectiveDate;
  private String email;
}
