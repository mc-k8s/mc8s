package com.mc8s.operator.crd;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.*;

import java.io.IOException;

public class MinecraftServerOperator {
  public static void main(String[] args) throws IOException {
    KubernetesClient client = new KubernetesClientBuilder().build();
    client
        .resources(MinecraftServerTemplate.class)
        .watch(new MinecraftServerTemplateController(client));
    client
        .resources(Deployment.class)
        .watch(new MinecraftServerTemplateDeploymentController(client));
  }
}
