package vn.com.msb.homeloan.core.repository.impl.criteria;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchPagination {

  private List<SearchCriteria> criteriaList;

  private int currentPage;

  private int pageSize;
}
