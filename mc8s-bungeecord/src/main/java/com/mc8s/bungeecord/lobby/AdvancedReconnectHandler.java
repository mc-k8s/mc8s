package com.mc8s.bungeecord.lobby;

import com.mc8s.bungeecord.server.GameServer;
import com.mc8s.bungeecord.server.PodWatcher;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.ReconnectHandler;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

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
    Optional<Map.Entry<UUID, GameServer>> optionalLobby =
        podWatcher.getGameServers().entrySet().stream()
            .filter(entry -> entry.getValue().getType().equalsIgnoreCase("LOBBY"))
            .findFirst();
    return optionalLobby
        .map(
            uuidGameServerEntry ->
                this.proxyServer.getServerInfo(uuidGameServerEntry.getKey().toString()))
        .orElse(null);
  }

  @Override
  public void setServer(ProxiedPlayer player) {}

  @Override
  public void save() {}

  @Override
  public void close() {}
}
