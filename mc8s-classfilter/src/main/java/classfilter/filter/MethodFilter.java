package classfilter.filter;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * @author Aventix created at: 14.03.2020 03:11
 */
public interface MethodFilter {
  boolean matches(final Method p0);

  static MethodFilter annotatedWith(final Class<? extends Annotation> annotation) {
    return filter -> filter.isAnnotationPresent(annotation);
  }
}
