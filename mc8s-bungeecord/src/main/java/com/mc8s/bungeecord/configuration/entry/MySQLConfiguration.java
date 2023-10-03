package com.mc8s.bungeecord.configuration.entry;

import com.google.inject.Singleton;
import com.mc8s.bungeecord.configuration.Config;
import com.mc8s.bungeecord.configuration.Configuration;

/**
 * @author Aventix created at: 20.07.2019
 */
@Singleton
@Configuration(filename = "mysql")
public class MySQLConfiguration extends Config {
  private String hostname = "localhost";
  private int port = 3306;
  private String username = "username";
  private String password = "password";
  private String database = "database";

  public String getHostname() {
    return hostname;
  }

  public int getPort() {
    return port;
  }

  public String getUsername() {
    return username;
  }

  public String getPassword() {
    return password;
  }

  public String getDatabase() {
    return database;
  }
}
