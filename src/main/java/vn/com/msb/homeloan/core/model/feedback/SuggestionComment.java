package vn.com.msb.homeloan.core.model.feedback;

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
public class SuggestionComment {

  String uuid;

  Integer rateFrom;

  Integer rateTo;

  String comment;

  Boolean status;
}
