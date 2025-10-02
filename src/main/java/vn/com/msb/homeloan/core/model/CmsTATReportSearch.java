package vn.com.msb.homeloan.core.model;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CmsTATReportSearch {

  List<CmsTATReport> contents;
  PagingResponse paging;
}
