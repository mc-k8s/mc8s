package com.mc8s.bungeecord;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import net.md_5.bungee.api.plugin.Plugin;

/**
 * @author Aventix created at: 20.11.2022
 */
public class Mc8sPlugin extends Plugin {
    public void onEnable() {
        KubernetesClient client = new KubernetesClientBuilder().build();
        client.resources(Pod.class).watch(new PodWatcher());
    }

    public void onDisable() {

    }
}
