package com.mc8s.bungeecord.configuration.entry;

import com.mc8s.bungeecord.configuration.Config;
import com.mc8s.bungeecord.configuration.Configuration;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/** @author Aventix created at: 15.11.2019 14:12 */
@Singleton
@Configuration(filename = "config")
public class BungeeConfiguration extends Config {
  private String controllerHostname = "127.0.0.1";
  private int controllerPort = 61153;
  private List<String> lobbyCommands = Arrays.asList("lobby", "l", "hub");

  public String getControllerHostname() {
    return controllerHostname;
  }

  public int getControllerPort() {
    return controllerPort;
  }

  public List<String> getLobbyCommands() {
    return lobbyCommands;
  }
}
