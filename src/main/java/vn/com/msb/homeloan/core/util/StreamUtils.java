package vn.com.msb.homeloan.core.util;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class StreamUtils {

  public static <T> Predicate<T> distinctBy(@NonNull final Function<? super T, ?> propSupplier) {
    final Set<Object> visitedElements = ConcurrentHashMap.newKeySet();
    return e -> visitedElements.add(propSupplier.apply(e));
  }
}
