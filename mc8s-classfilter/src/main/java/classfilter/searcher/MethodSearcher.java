package classfilter.searcher;

import classfilter.filter.MethodFilter;
import com.google.common.base.Preconditions;
import com.google.common.reflect.ClassPath;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.name.Names;

import javax.inject.Singleton;
import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/** @author Aventix created at: 14.03.2020 03:13 */
@Singleton
public class MethodSearcher {
  private final Injector injector;

  @Inject
  public MethodSearcher(Injector injector) {
    this.injector = injector;
  }

  public Stream<Method> filter(String[] packageNames, MethodFilter... methodFilters) {
    Preconditions.checkNotNull(packageNames);
    try {
      ClassPath classPath =
          ClassPath.from(
              injector.getInstance(Key.get(ClassLoader.class, Names.named("SearcherClassLoader"))));
      return (packageNames.length == 0
              ? classPath.getAllClasses()
              : Arrays.stream(packageNames)
              .flatMap(name -> classPath.getTopLevelClassesRecursive(name).stream()).toList())
          .stream()
              .map(
                  classInfo -> {
                    try {
                      return classInfo.load();
                    } catch (Throwable throwable) {
                      return null;
                    }
                  })
              .filter(Objects::nonNull)
              .flatMap(clazz -> Arrays.stream(clazz.getDeclaredMethods()))
              .filter(
                  method ->
                      Arrays.stream(methodFilters).allMatch(filter -> filter.matches(method)));
    } catch (IOException e) {
      e.printStackTrace();
      return null;
    }
  }
}
