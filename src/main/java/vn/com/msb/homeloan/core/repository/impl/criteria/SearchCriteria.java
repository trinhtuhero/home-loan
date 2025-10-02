package vn.com.msb.homeloan.core.repository.impl.criteria;

import java.io.Serializable;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchCriteria implements Serializable {

  private String key;
  private String operation;
  private transient Object value;
  private boolean orPredicate;
}
