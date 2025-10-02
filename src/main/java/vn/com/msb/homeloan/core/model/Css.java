package vn.com.msb.homeloan.core.model;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class Css {

  String uuid;

  String loanApplicationId;

  Integer profileId;

  String score;

  String grade;

  Date scoringDate;

  String metaData;
}
