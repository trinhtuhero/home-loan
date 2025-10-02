package vn.com.msb.homeloan.api.dto.response.feedback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
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
public class FeedbackInitFormResponse {

  Integer rate;

  @JsonIgnoreProperties(value = {"rate_from", "rate_to", "status"})
  List<SuggestionCommentResponse> comments;
}
