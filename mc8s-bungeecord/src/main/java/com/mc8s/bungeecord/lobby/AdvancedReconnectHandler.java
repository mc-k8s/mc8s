package com.mc8s.bungeecord.lobby;

import com.google.common.base.Preconditions;
import com.mc8s.bungeecord.server.PodWatcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Aventix created at: 23.11.2022
 */
@Singleton
public class AdvancedReconnectHandler implements ReconnectHandler {
  private final PodWatcher podWatcher;
  private final ProxyServer proxyServer;

  @Inject
  public AdvancedReconnectHandler(PodWatcher podWatcher, ProxyServer proxyServer) {
    this.podWatcher = podWatcher;
    this.proxyServer = proxyServer;
  }

  @Override
  public ServerInfo getServer(ProxiedPlayer player) {
    System.out.println("GETTING LOBBY FOR " + player.getDisplayName());
    ServerInfo lobby =
        podWatcher.getGameServers().entrySet().stream()
            .filter(entry -> entry.getValue().getType().equalsIgnoreCase("LOBBY"))
            .findFirst()
            .map(
                uuidGameServerEntry ->
                    this.proxyServer.getServerInfo(uuidGameServerEntry.getKey().toString()))
            .orElse(null);

    System.out.println(
        "FOUND LOBBY: "
            + lobby.getName()
            + " with address : "
            + lobby.getAddress().getAddress().toString());
    if (lobby == null) {
      player.disconnect("NEW MESSAGE IN BASE COMPONENT //:TODO");
    }

    return lobby;
  }

  @Override
  public void setServer(ProxiedPlayer player) {}

  @Override
  public void save() {}

  @Override
  public void close() {}
}
