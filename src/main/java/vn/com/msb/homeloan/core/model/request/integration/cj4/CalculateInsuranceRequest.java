package vn.com.msb.homeloan.core.model.request.integration.cj4;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategy.class)
public class CalculateInsuranceRequest {

  String productId;
  String dob;
  Integer gender;
  Long monthlyIncome;
  Boolean isGenIllustrate;
}
