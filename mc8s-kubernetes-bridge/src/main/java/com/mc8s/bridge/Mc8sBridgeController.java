package com.mc8s.bridge;

import com.mc8s.bridge.group.Mc8sGameServer;
import io.fabric8.kubernetes.client.KubernetesClient;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * @author Aventix created at: 21.11.2022
 */
@Singleton
public class Mc8sBridgeController {
    private final KubernetesClient kubernetesClient;

    @Inject
    public Mc8sBridgeController(KubernetesClient kubernetesClient) {
        this.kubernetesClient = kubernetesClient;
    }


}
