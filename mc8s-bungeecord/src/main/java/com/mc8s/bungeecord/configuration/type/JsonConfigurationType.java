package com.mc8s.bungeecord.configuration.type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.mc8s.bungeecord.configuration.Config;
import com.mc8s.bungeecord.configuration.Configuration;

import java.io.*;
import java.lang.reflect.Field;

/** @author Aventix created at: 20.07.2019 */
public class JsonConfigurationType implements ConfigurationType {
  private final Injector injector;

  @Inject
  public JsonConfigurationType(Injector injector) {
    this.injector = injector;
  }

  private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

  public void onLoad(Class<? extends Config> clazz) {
    Configuration annotation = clazz.getAnnotation(Configuration.class);
    File file;
    if (!annotation.path().isEmpty()) {
      file = new File("./" + annotation.path(), annotation.filename() + "." + this.fileExtension());
    } else {
      file = new File(".", annotation.filename() + "." + this.fileExtension());
    }

    try {
      if (!file.exists()) {
        if (!file.getParentFile().exists()) {
          file.getParentFile().mkdirs();
        }
        if (!file.createNewFile()) {
          System.out.println("Can't create config file!");
        } else {
          System.out.println("File doesnt exist, save new file...");
          this.onSave(clazz);
          return;
        }
      }

      Config config = GSON.fromJson(new BufferedReader(new FileReader(file)), clazz);
      Config instance = this.injector.getInstance(clazz);

      for (Field declaredField : clazz.getDeclaredFields()) {
        declaredField.setAccessible(true);
        if (instance != null && config != null && declaredField.get(config) != null)
          declaredField.set(instance, declaredField.get(config));
      }
    } catch (Exception e) {
      System.out.println("Exception when try to load config");
      e.printStackTrace();
    }
  }

  public void onSave(Class<? extends Config> clazz) {
    Configuration annotation = clazz.getAnnotation(Configuration.class);
    File file;
      if (!annotation.path().isEmpty()) {
          file = new File("./" + annotation.path(), annotation.filename() + "." + this.fileExtension());
      } else {
          file = new File(".", annotation.filename() + "." + this.fileExtension());
      }

    if (!file.exists() && !file.mkdirs()) {
      System.out.println("Can't create folder!");
      return;
    }

    try (Writer writer = new FileWriter(file)) {
      GSON.toJson(injector.getInstance(clazz), writer);
    } catch (IOException e) {
      System.out.println("Can't save config " + file.getName());
      e.printStackTrace();
    }
  }

  public String fileExtension() {
    return "json";
  }
}
