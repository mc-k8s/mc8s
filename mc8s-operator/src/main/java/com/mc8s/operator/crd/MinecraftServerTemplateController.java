package com.mc8s.operator.crd;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.DeploymentBuilder;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;
import io.javaoperatorsdk.operator.api.reconciler.Context;
import io.javaoperatorsdk.operator.api.reconciler.ControllerConfiguration;
import io.javaoperatorsdk.operator.api.reconciler.Reconciler;
import io.javaoperatorsdk.operator.api.reconciler.UpdateControl;

@ControllerConfiguration
public class MinecraftServerTemplateController implements Watcher<MinecraftServerTemplate> {
  private final KubernetesClient client;

  public MinecraftServerTemplateController(KubernetesClient client) {
    this.client = client;
  }


  @Override
  public void eventReceived(Action action, MinecraftServerTemplate resource) {
    try {
      if (action == Action.ADDED || action == Action.MODIFIED) {
        client.resources(Deployment.class).resource(createDeployment(resource)).createOrReplace();
      } else if (action == Action.DELETED) {
        client.resources(Deployment.class).inNamespace(resource.getMetadata().getNamespace()).withName(resource.getMetadata().getName()).delete();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  public Deployment createDeployment(MinecraftServerTemplate resource) {
    DeploymentBuilder deploymentBuilder = new DeploymentBuilder();
    this.addMetaData(deploymentBuilder, resource);
    this.addSpec(deploymentBuilder, resource);
    return deploymentBuilder.build();
  }

  public void addMetaData(DeploymentBuilder builder, MinecraftServerTemplate resource) {
    builder.withNewMetadata()
        .withName(resource.getMetadata().getName())
        .withNamespace(resource.getMetadata().getNamespace())
        .addToLabels("app", "minecraft-lobby")
        .addNewOwnerReference()
        .withKind("MinecraftServerTemplate")
        .withApiVersion("com.mc8s.operator/v1alpha1")
        .withUid(resource.getMetadata().getUid())
        .withName(resource.getMetadata().getName())
        .withController()
        .withBlockOwnerDeletion()
        .endOwnerReference()
        .endMetadata();
  }

  public void addSpec(DeploymentBuilder builder, MinecraftServerTemplate resource) {
    builder
        .withNewSpec()
          .withReplicas(resource.getSpec().getReplicas())
          .withNewSelector()
           .addToMatchLabels("app", "minecraft-lobby")
          .endSelector()
          .withNewTemplate()
            .withNewMetadata()
            .addToLabels("app", "minecraft-lobby")
            .endMetadata()
            .withNewSpec()
            .withImagePullSecrets(resource.getSpec().getImagePullSecrets())
            .addNewContainer()
            .withName("minecraft")
            .withImage(resource.getSpec().getImage())
            .withImagePullPolicy(resource.getSpec().getImagePullPolicy())
            .addNewPort()
            .withName("minecraft")
            .withContainerPort(25565)
            .withProtocol("TCP")
            .endPort()
            .endContainer()
            .endSpec()
          .endTemplate()
        .endSpec();
  }

  @Override
  public void onClose(WatcherException cause) {

  }

}
