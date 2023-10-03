package com.mc8s.bungeecord.configuration.type;

import com.mc8s.bungeecord.configuration.Config;

/**
 * @author Aventix created at: 14.07.2019
 */
public interface ConfigurationType {
  void onLoad(Class<? extends Config> clazz);

  void onSave(Class<? extends Config> clazz);

  String fileExtension();
}
