package com.mc8s.operator.crd;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

import java.util.Objects;

@ControllerConfiguration
public class MinecraftServerTemplateDeploymentController implements Watcher<Deployment> {
  private final KubernetesClient client;

  public MinecraftServerTemplateDeploymentController(KubernetesClient client) {
    this.client = client;
  }

  @Override
  public void eventReceived(Action action, Deployment resource) {
    if (action == Action.ADDED) {
      if (Objects.equals(resource.getMetadata().getLabels().get("app"), "minecraft-lobby")) {
        if (client
                .resources(MinecraftServerTemplate.class)
                .inNamespace(resource.getMetadata().getNamespace())
                .withName(resource.getMetadata().getName())
                .get()
            == null) {
          client.resources(Deployment.class).resource(resource).delete();
        }
      }
    }
  }

  @Override
  public void onClose(WatcherException cause) {}
}
