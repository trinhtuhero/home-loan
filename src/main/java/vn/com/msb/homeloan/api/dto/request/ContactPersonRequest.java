package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactPersonRequest {

  String uuid;

  @NotBlank
  String loanId;

  @NotBlank
  @Pattern(regexp = "^(WIFE|HUSBAND|FATHER|FATHER_IN_LAW|MOTHER|MOTHER_IN_LAW|SIBLINGS|SIBLINGS_IN_LAW|SON|STEP_CHILD|HOUSEHOLD_MEM|CAPITAL_CONTRIBUTING_MEM|NEIGHTBOR|FRIEND|FELLOW_SHIP|OTHERS)$")
  String type;

  @NotBlank
  String fullName;

  @NotBlank
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;
}
