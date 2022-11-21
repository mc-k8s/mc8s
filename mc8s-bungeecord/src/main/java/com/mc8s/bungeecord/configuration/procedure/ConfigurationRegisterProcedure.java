package com.mc8s.bungeecord.configuration.procedure;

import classfilter.filter.TypeFilter;
import classfilter.searcher.TypeSearcher;
import com.google.common.base.Preconditions;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.mc8s.bungeecord.Mc8sPlugin;
import com.mc8s.bungeecord.configuration.Config;
import com.mc8s.bungeecord.configuration.Configuration;
import com.mc8s.bungeecord.configuration.type.ConfigurationType;

/** @author Aventix created at: 14.07.2019 */
@Singleton
public class ConfigurationRegisterProcedure {
  private final TypeSearcher typeSearcher;
  private final Injector injector;

  @Inject
  public ConfigurationRegisterProcedure(TypeSearcher typeSearcher, Injector injector) {
    this.typeSearcher = typeSearcher;
    this.injector = injector;
  }

  public void initialize(String... packageNames) {
    Preconditions.checkNotNull(packageNames);

    this.typeSearcher
        .filter(
            Mc8sPlugin.class.getClassLoader(),
            packageNames,
            TypeFilter.subclassOf(Config.class),
            TypeFilter.annotatedWith(Configuration.class))
        .forEach(
            clazz -> {
              Configuration annotation = clazz.getAnnotation(Configuration.class);
              ConfigurationType type = injector.getInstance(annotation.type());
              Config config = (Config) injector.getInstance(clazz);

              type.onLoad(config.getClass());
              System.out.println(
                  "Successfully loaded configuration "
                      + annotation.filename()
                      + "."
                      + type.fileExtension());
            });
  }
}
