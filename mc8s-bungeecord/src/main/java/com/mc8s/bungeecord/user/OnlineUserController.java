package com.mc8s.bungeecord.user;

import io.fabric8.kubernetes.api.model.KubernetesResourceList;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

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
    KubernetesResourceList<Pod> hostname =
        this.kubernetesClient
            .resources(Pod.class)
            .inAnyNamespace()
            .withField("metadata.name", System.getenv("HOSTNAME"))
            .list();
    System.out.println("Size: " + hostname.getItems().size());

    hostname
        .getItems()
        .forEach(
            pod -> {
              this.kubernetesClient
                  .resources(Pod.class)
                  .resource(pod)
                  .edit(
                      pod1 -> {
                        pod1.getMetadata()
                            .getAnnotations()
                            .put(
                                "current-users", String.valueOf(this.proxyServer.getOnlineCount()));
                        return pod1;
                      });
            });
  }

  @EventHandler
  public void onLeave(PlayerDisconnectEvent event) {
    this.updatePlayerCount();
  }
}
