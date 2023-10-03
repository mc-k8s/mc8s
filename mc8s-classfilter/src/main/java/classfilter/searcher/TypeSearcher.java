package classfilter.searcher;

import classfilter.filter.TypeFilter;
import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;

import javax.inject.Singleton;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Aventix created at: 14.03.2020 03:14
 */
@Singleton
public class TypeSearcher {
  public Stream<? extends Class<?>> filter(
      ClassLoader classLoader, Collection<String> packageNames, TypeFilter... typeFilters) {
    Preconditions.checkNotNull(classLoader);
    Preconditions.checkNotNull(packageNames);
    try {
      ClassPath classPath = ClassPath.from(classLoader);
      Preconditions.checkNotNull(classPath);
      return (packageNames.size() == 0
              ? classPath.getAllClasses()
              : packageNames.stream()
                  .flatMap(name -> classPath.getTopLevelClassesRecursive(name).stream())
                  .toList())
          .stream()
              .map(
                  classInfo -> {
                    try {
                      return classInfo.load();
                    } catch (Throwable t) {
                      return null;
                    }
                  })
              .filter(Objects::nonNull)
              .filter(
                  clazz ->
                      Arrays.stream(typeFilters).allMatch(typeFilter -> typeFilter.matches(clazz)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }

  public Stream<? extends Class<?>> filter(
      ClassLoader classLoader, String[] packageNames, TypeFilter... typeFilters) {
    return this.filter(classLoader, Arrays.asList(packageNames), typeFilters);
  }
}
