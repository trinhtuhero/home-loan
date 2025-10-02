package vn.com.msb.homeloan.core.repository.impl.criteria.response;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchResponse<T> {

  private List<T> data;

  private int totalPage;

  private long totalElements;
}
