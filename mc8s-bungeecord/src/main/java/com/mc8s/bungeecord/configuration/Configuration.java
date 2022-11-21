package com.mc8s.bungeecord.configuration;

import com.mc8s.bungeecord.configuration.type.ConfigurationType;
import com.mc8s.bungeecord.configuration.type.JsonConfigurationType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** @author Aventix created at: 14.07.2019 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
  String path() default "";

  String filename();

  Class<? extends ConfigurationType> type() default JsonConfigurationType.class;
}
