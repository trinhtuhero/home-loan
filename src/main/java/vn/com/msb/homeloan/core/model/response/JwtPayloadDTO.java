package vn.com.msb.homeloan.core.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtPayloadDTO {

  @JsonProperty("name")
  private String name;

  @JsonProperty("preferred_username")
  private String preferredUsername;

  @JsonProperty("given_name")
  private String givenName;

  @JsonProperty("family_name")
  private String familyName;

  @JsonProperty("email")
  private String email;
}
