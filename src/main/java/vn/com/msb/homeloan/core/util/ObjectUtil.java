package vn.com.msb.homeloan.core.util;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectUtil {

  public static boolean isEmpty(Object param) {
    if (Objects.isNull(param)) {
      return true;
    }
    if (param instanceof String) {
      return ((String) param).length() == 0 || "".equals(param.toString().trim());
    }
    if (param instanceof Collection) {
      return ((Collection<?>) param).isEmpty();
    }
    if (param instanceof Map) {
      return ((Map<?, ?>) param).isEmpty();
    }
    if (param instanceof Object[]) {
      return ((Object[]) param).length == 0;
    }
    return false;
  }

  public static boolean isNotEmpty(Object param) {
    return !isEmpty(param);
  }
}
