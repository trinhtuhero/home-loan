package vn.com.msb.homeloan.api.dto.request;

import java.util.List;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;

@Validated
@Data
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
public class CMSExportReportRequest {

  String loanCode;
  List<String> status;
  String branchCode;
  String dateFrom;
  String dateTo;
  String loanPurpose;

  @NotBlank
  @Pattern(regexp = "^(TAT|FEEDBACK)$", message = "must match TAT|FEEDBACK")
  String type;
}
