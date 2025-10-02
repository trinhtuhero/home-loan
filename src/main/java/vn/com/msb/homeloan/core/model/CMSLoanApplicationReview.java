package vn.com.msb.homeloan.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CMSLoanApplicationReview extends LoanApplicationReview {

  CreditAppraisal creditAppraisal;
  List<CMSGetCicInfo> cics;
  AssetEvaluate assetEvaluate;
  List<OtherEvaluate> otherEvaluates;
  List<FieldSurveyItem> fieldSurveyItems;
  List<ExceptionItem> exceptionItems;
  List<CreditworthinessItem> creditworthinessItems;
  List<FileConfigCategory> files;
}
