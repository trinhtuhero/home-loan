package vn.com.msb.homeloan.api.dto.response;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import vn.com.msb.homeloan.api.dto.request.LandTransactionRequest;

@Getter
@Setter
@ToString(callSuper = true)
public class LandTransactionResponse extends LandTransactionRequest {

  private Instant createdAt;
  private Instant updatedAt;
}
