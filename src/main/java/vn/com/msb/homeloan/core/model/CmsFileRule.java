package vn.com.msb.homeloan.core.model;

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
public class CmsFileRule {

  String uuid;

  String expression;

  String description;

  //Instant createdAt;

  //Instant updatedAt;
}
