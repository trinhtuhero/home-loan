package vn.com.msb.homeloan.core.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "suggestion_comment", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class SuggestionCommentEntity extends BaseEntity {

  // rate_from
  @Column(name = "rate_from")
  Integer rateFrom;
  // rate_to
  @Column(name = "rate_to")
  Integer rateTo;
  // comment
  @Column(name = "comment")
  String comment;
  // status
  @Column(name = "status")
  Boolean status;
}
