package com.mc8s.bungeecord.server;

import com.google.common.collect.Maps;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;

/**
 * @author Aventix created at: 20.11.2022
 */
@Singleton
public class PodWatcher implements Watcher<Pod> {
    private final ProxyServer proxyServer;
    private final Map<UUID, GameServer> gameServers = Maps.newConcurrentMap();

    @Inject
    public PodWatcher(ProxyServer proxyServer) {
        this.proxyServer = proxyServer;
    }

    public Map<UUID, GameServer> getGameServers() {
        return gameServers;
    }

    @Override
    public void eventReceived(Action action, Pod resource) {
        if (!resource.getMetadata().getLabels().containsKey("minecraft-template-name")) {
            return;
        }

        if (action.equals(Action.ADDED) || action.equals(Action.MODIFIED)) {
            if (!resource.getStatus().getPhase().equalsIgnoreCase("Running")) {
                this.gameServers.remove(UUID.fromString(resource.getMetadata().getUid()));
                return;
            }

            if (resource.getStatus().getPodIPs().isEmpty()) return;

            resource.getSpec().getContainers().stream()
                    .filter(container -> container.getPorts().stream().anyMatch(port -> port.getName().equals("minecraft"))).flatMap(container -> container.getPorts().stream())
                    .forEach(port -> {
                        this.gameServers.put(
                                UUID.fromString(resource.getMetadata().getUid()),
                                new GameServer(
                                        resource.getMetadata().getLabels().get("minecraft-template-name"),
                                        new InetSocketAddress(resource.getStatus().getPodIP(), port.getContainerPort())
                                )
                        );
                        ServerInfo serverInfo =
                                this.proxyServer.constructServerInfo(
                                        resource.getMetadata().getUid(),
                                        new InetSocketAddress(resource.getStatus().getPodIP(), port.getContainerPort()),
                                        resource.getMetadata().getLabels().get("minecraft-template-name"),
                                        false);
                        synchronized (this.proxyServer.getServers()) {
                            this.proxyServer.getServers().put(serverInfo.getName(), serverInfo);
                        }
                    });
        } else if (action.equals(Action.DELETED)) {
            this.gameServers.remove(UUID.fromString(resource.getMetadata().getUid()));
        }
    }

    @Override
    public void onClose(WatcherException cause) {

    }
}
