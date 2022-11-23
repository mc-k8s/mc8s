package com.mc8s.operator.crd;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.fabric8.kubernetes.api.model.LocalObjectReference;
import io.fabric8.kubernetes.api.model.Namespaced;
import io.fabric8.kubernetes.client.CustomResource;
import io.fabric8.kubernetes.model.annotation.Group;
import io.fabric8.kubernetes.model.annotation.Version;

import java.util.ArrayList;
import java.util.List;

@Version("v1alpha1")
@Group("com.mc8s.operator")
public class MinecraftServerTemplate extends CustomResource<MinecraftServerTemplate.Spec, MinecraftServerTemplate.Status> implements Namespaced {
  public static class Spec {
    @JsonProperty("image")
    private String image;

    @JsonProperty("replicas")
    private int replicas;

    @JsonProperty("imagePullPolicy")
    private String imagePullPolicy;

    @JsonProperty("imagePullSecrets")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<LocalObjectReference> imagePullSecrets = new ArrayList<>();

    @JsonProperty("netherDisabled")
    private boolean netherDisabled = true;

    @JsonProperty("endDisabled")
    private boolean endDisabled = true;

    @JsonProperty("mapTemplatePVCName")
    private String mapTemplatePVCName;

    public String getMapTemplatePVCName(){
      return this.mapTemplatePVCName;
    }

    public void setMapTemplatePVCName(String mapTemplatePVCName) {
      this.mapTemplatePVCName = mapTemplatePVCName;
    }

    public String getImage() {
      return image;
    }

    public int getReplicas() {
      return replicas;
    }

    public boolean isNetherDisabled() {return this.netherDisabled; }

    public void setNetherDisabled(boolean netherDisabled){
      this.netherDisabled = netherDisabled;
    }

    public String getImagePullPolicy() {
      return imagePullPolicy;
    }

    public List<LocalObjectReference> getImagePullSecrets() {
      return imagePullSecrets;
    }

    public boolean isEndDisabled() {
      return endDisabled;
    }

    public void setEndDisabled(boolean endDisabled) {
      this.endDisabled = endDisabled;
    }
  }

  public static class Status {

  }
}
