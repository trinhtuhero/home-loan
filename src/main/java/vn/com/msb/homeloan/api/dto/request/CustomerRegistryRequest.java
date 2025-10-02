package vn.com.msb.homeloan.api.dto.request;


import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@ToString
public class CustomerRegistryRequest {

  @NotNull
  @Length(max = 255, message = "customerName must be less than or equal to 255")
  private String customerName;

  @NotNull
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  private String customerPhone;

  private String product;

  private String provinceCode;
}
