package com.mc8s.bungeecord.configuration;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mc8s.bungeecord.configuration.type.ConfigurationType;

/** @author Aventix created at: 14.07.2019 */
public class Config {
  @Inject private transient Injector injector;

  public void save() {
    Configuration annotation = this.getClass().getAnnotation(Configuration.class);
    ConfigurationType type = injector.getInstance(annotation.type());
    type.onSave(this.getClass());
  }
}
