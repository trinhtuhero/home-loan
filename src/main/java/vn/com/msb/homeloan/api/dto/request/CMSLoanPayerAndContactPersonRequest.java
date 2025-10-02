package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CMSLoanPayerAndContactPersonRequest {

  @NotNull
  @Valid
  List<CMSLoanPayerRequest> loanPayers;

  @NotNull
  @Valid
  CMSContactPersonRequest contactPerson;
}
