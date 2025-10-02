package vn.com.msb.homeloan.api.dto.request;

import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedbackRequest {

  String loanApplicationId;

  Integer rate;

  @NotNull
  String[] comments;

  String additionalComment;
}
