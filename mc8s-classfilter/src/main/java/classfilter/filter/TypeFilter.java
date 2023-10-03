package classfilter.filter;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author Aventix created at: 21.07.2019
 */
public interface TypeFilter {
  boolean matches(Class<?> clazz);

  static TypeFilter subclassOf(Class<?> superclass) {
    return clazz -> !superclass.equals(clazz) && superclass.isAssignableFrom(clazz);
  }

  static TypeFilter annotatedWith(Class<? extends Annotation> annotation) {
    return clazz -> clazz.isAnnotationPresent(annotation);
  }

  static TypeFilter matchesAnyMethod(MethodFilter... methodFilters) {
    return clazz ->
        Arrays.stream(clazz.getDeclaredMethods())
            .anyMatch(
                method ->
                    Arrays.stream(methodFilters)
                        .allMatch(methodFilter -> methodFilter.matches(method)));
  }
}
