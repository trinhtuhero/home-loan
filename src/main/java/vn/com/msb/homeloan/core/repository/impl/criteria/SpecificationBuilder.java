package vn.com.msb.homeloan.core.repository.impl.criteria;

import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class SpecificationBuilder<T> {

  public Specification<T> buildCriteria(List<SearchCriteria> params) {
    if (params == null) {
      return null;
    }
    List<Specification<T>> specs =
        params.stream().map(ObjectSpecification<T>::new).collect(Collectors.toList());
    Specification<T> specification = null;
    for (int i = 0; i < params.size(); i++) {
      specification =
          params.get(i).isOrPredicate() ? Specification.where(specification).or(specs.get(i))
              : Specification.where(specification).and(specs.get(i));
    }
    return specification;
  }
}
