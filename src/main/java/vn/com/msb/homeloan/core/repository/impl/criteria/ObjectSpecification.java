package vn.com.msb.homeloan.core.repository.impl.criteria;

import java.sql.Timestamp;
import java.util.Date;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import vn.com.msb.homeloan.core.util.DateUtils;

@AllArgsConstructor
@Slf4j
public class ObjectSpecification<T> implements Specification<T> {

  private SearchCriteria criteria;

  @Override
  public Predicate toPredicate(@NonNull Root<T> root, @NonNull CriteriaQuery<?> query,
      @NonNull CriteriaBuilder builder) {
    Predicate pre = null;
    Class<?> fieldType = root.get(criteria.getKey()).getJavaType();
    Object value = castToRequiredType(fieldType, criteria.getValue());
    String key = criteria.getKey();
    if (value != null) {
      switch (criteria.getOperation()) {
        case ">":
          pre = builder.greaterThan(root.get(key), value.toString());
          break;
        case "<":
          pre = builder.lessThan(root.get(key), value.toString());
          break;
        case ">=":
          pre = builder.greaterThanOrEqualTo(root.get(key), value.toString());
          break;
        case "<=":
          pre = builder.lessThanOrEqualTo(root.get(key), value.toString());
          break;
        case ":":
          if (fieldType == String.class) {
            pre = builder.like(builder.upper(root.get(key)),
                "%" + value.toString().toUpperCase() + "%");
          } else {
            pre = builder.equal(root.get(key), value);
          }
          break;
        case "in":
          pre = root.get(key).in(value);
          break;
        default:
          break;
      }
    }
    return pre;
  }

  private Object castToRequiredType(@NonNull Class<?> type, Object value) {
    try {
      if (type.isAssignableFrom(Timestamp.class)) {
        return new Timestamp(DateUtils.convertToDateFormat(value.toString()).getTime());
      } else if (type.isAssignableFrom(Date.class)) {
        return DateUtils.convertToDateFormat(value.toString());
      }
    } catch (Exception e) {
      log.error("Cast failed: ", e);
      return null;
    }
    return value;
  }
}
