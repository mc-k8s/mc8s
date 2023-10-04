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
      if (action == Action.ADDED || action == Action.MODIFIED) {;
        client.resources(Deployment.class).resource(createDeployment(resource)).createOrReplace();
      } else if (action == Action.DELETED) {
        client
            .resources(Deployment.class)
            .inNamespace(resource.getMetadata().getNamespace())
            .withName(resource.getMetadata().getName())
            .delete();
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
    builder
        .withNewMetadata()
        .withName(resource.getMetadata().getName())
        .withNamespace(resource.getMetadata().getNamespace())
        .addToLabels("minecraft-template-name", resource.getMetadata().getName())
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
        .addToMatchLabels("minecraft-template-name", resource.getMetadata().getName())
        .endSelector()
        .withNewTemplate()
        .withNewMetadata()
        .addToLabels("minecraft-template-name", resource.getMetadata().getName())
        .endMetadata()
        .withNewSpec()
        .withImagePullSecrets(resource.getSpec().getImagePullSecrets())
        .withServiceAccountName("minecraft-controller")
        .addNewVolume()
        .withName("map-template")
        .withNewPersistentVolumeClaim()
        .withClaimName(resource.getSpec().getMapTemplatePVCName())
        .endPersistentVolumeClaim()
        .endVolume()
        .addNewContainer()
        .withName("minecraft")
        .withImage(resource.getSpec().getImage())
        .withImagePullPolicy(resource.getSpec().getImagePullPolicy())
        .addNewEnv()
        .withName("MC8S_DISABLE_END")
        .withValue(String.valueOf(resource.getSpec().isEndDisabled()))
        .endEnv()
        .addNewEnv()
        .withName("MC8S_DISABLE_NETHER")
        .withValue(String.valueOf(resource.getSpec().isNetherDisabled()))
        .endEnv()
        .addNewPort()
        .withName("minecraft")
        .withContainerPort(25565)
        .withProtocol("TCP")
        .endPort()
        .addNewVolumeMount()
        .withMountPath("/var/map-template")
        .withName("map-template")
        .withReadOnly(true)
        .endVolumeMount()
        .endContainer()
        .endSpec()
        .endTemplate()
        .endSpec();
  }

  @Override
  public void onClose(WatcherException cause) {}
}
