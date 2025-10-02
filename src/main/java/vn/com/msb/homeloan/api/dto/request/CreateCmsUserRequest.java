package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateCmsUserRequest {

  @NotBlank
  @Length(max = 10)
  String emplId;

  @NotBlank
  @Email
  @Length(max = 100)
  String email;

  @NotBlank
  @Length(max = 150)
  String fullName;

  @NotBlank(message = "Phone must not be null")
  @Pattern(regexp = "((0)[3|5|7|8|9])+([0-9]{8})\\b", message = "must match phone pattern")
  String phone;

  @NotBlank
  String branchCode;

  @Email
  @Length(max = 100)
  String leaderEmail;

  @NotBlank
  String role;
}
