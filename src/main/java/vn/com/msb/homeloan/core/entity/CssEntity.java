package vn.com.msb.homeloan.core.entity;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@Entity
@Table(name = "css", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class CssEntity extends BaseEntity {

  @Column(name = "loan_application_id")
  String loanApplicationId;

  @Column(name = "profile_id")
  Integer profileId;

  @Column(name = "score")
  String score;

  @Column(name = "grade")
  String grade;

  @Column(name = "scoring_date")
  Date scoringDate;

  @Column(name = "meta_data")
  String metaData;
}
