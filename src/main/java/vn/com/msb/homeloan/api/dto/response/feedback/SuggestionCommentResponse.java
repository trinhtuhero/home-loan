package vn.com.msb.homeloan.api.dto.response.feedback;

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
public class SuggestionCommentResponse {

  String uuid;

  Integer rateFrom;

  Integer rateTo;

  String comment;

  Boolean status;
}
