package vn.com.msb.homeloan.api.dto.request.insurance;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateLeadToCJ4Request {

  @NotBlank
  String loanId;

  String emplId;

  @NotBlank
  @Pattern(regexp = "^(INTERESTED|NOT_INTERESTED)$", message = "must match pattern INTERESTED|NOT_INTERESTED")
  String status;
}
