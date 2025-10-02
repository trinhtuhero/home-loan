package vn.com.msb.homeloan.api.dto.request;


import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanAdviseStatusRequest {

  @Pattern(regexp = "CONTACTED|NOT_CONTACTED_YET", message = "currentStatus not match")
  private String currentStatus;
}
