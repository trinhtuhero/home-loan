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
@Table(name = "feedback_suggestion_map", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class FeedbackSuggestionMapEntity extends BaseEntity {

  // feedback_id
  @Column(name = "feedback_id")
  String feedbackId;

  // suggestion_comment_id
  @Column(name = "suggestion_comment_id")
  String suggestionCommentId;
}
