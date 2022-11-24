package com.mc8s.bungeecord.user;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Aventix created at: 24.11.2022
 */
@Singleton
public class OnlineUserController implements Listener {
    private final ProxyServer proxyServer;
    private final KubernetesClient kubernetesClient;

    @Inject
    public OnlineUserController(ProxyServer proxyServer, KubernetesClient kubernetesClient) {
        this.proxyServer = proxyServer;
        this.kubernetesClient = kubernetesClient;
    }

    @EventHandler
    public void onEvent(PostLoginEvent event) {
        this.updatePlayerCount();
    }

    public synchronized void updatePlayerCount() {
        this.kubernetesClient.resources(Pod.class).inAnyNamespace().withField("metadata.name", System.getenv("HOSTNAME")).list().getItems().forEach(pod -> {
            pod.getStatus().setAdditionalProperty("current-users", this.proxyServer.getOnlineCount());
            this.kubernetesClient.resources(Pod.class).resource(pod).createOrReplace();
        });
    }

    @EventHandler
    public void onLeave(PlayerDisconnectEvent event) {
        this.updatePlayerCount();
    }
}
