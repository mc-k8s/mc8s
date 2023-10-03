package com.mc8s.bungeecord.configuration.entry;

import com.mc8s.bungeecord.configuration.Config;
import com.mc8s.bungeecord.configuration.Configuration;

import javax.inject.Singleton;
import java.util.Arrays;
import java.util.List;

/**
 * @author Aventix created at: 15.11.2019 14:12
 */
@Singleton
@Configuration(filename = "config")
public class BungeeConfiguration extends Config {
  private List<String> lobbyCommands = Arrays.asList("lobby", "l", "hub");

  public List<String> getLobbyCommands() {
    return lobbyCommands;
  }
}
