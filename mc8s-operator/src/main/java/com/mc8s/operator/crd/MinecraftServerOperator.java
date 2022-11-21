package com.mc8s.operator.crd;

import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.client.*;
import io.javaoperatorsdk.operator.Operator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MinecraftServerOperator {
  public static void main(String[] args) throws IOException {
    KubernetesClient client = new KubernetesClientBuilder().build();
    client.resources(MinecraftServerTemplate.class).watch(new MinecraftServerTemplateController(client));
    client.resources(Deployment.class).watch(new MinecraftServerTemplateDeploymentController(client));
  }
}
