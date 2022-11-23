package com.mc8s.bungeecord.server;

import com.google.common.collect.Maps;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

import javax.inject.Singleton;
import java.net.InetSocketAddress;
import java.util.Map;
import java.util.UUID;

/**
 * @author Aventix created at: 20.11.2022
 */
@Singleton
public class PodWatcher implements Watcher<Pod> {
    private final Map<UUID, GameServer> gameServers = Maps.newConcurrentMap();

    public Map<UUID, GameServer> getGameServers() {
        return gameServers;
    }

    @Override
    public void eventReceived(Action action, Pod resource) {
        if (!resource.getMetadata().getLabels().containsKey("minecraft-template-name")) {
            return;
        }

        if (action.equals(Action.ADDED) || action.equals(Action.MODIFIED)) {
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
                    });
        } else if (action.equals(Action.DELETED)) {
            this.gameServers.remove(UUID.fromString(resource.getMetadata().getUid()));
        }
    }

    @Override
    public void onClose(WatcherException cause) {

    }
}
