package com.mc8s.bungeecord;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.name.Names;
import com.mc8s.bungeecord.configuration.entry.BungeeConfiguration;
import com.mc8s.bungeecord.configuration.procedure.ConfigurationRegisterProcedure;
import com.mc8s.bungeecord.lobby.AdvancedReconnectHandler;
import com.mc8s.bungeecord.server.PodWatcher;
import com.mc8s.bungeecord.user.OnlineUserController;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.KubernetesClient;
import io.fabric8.kubernetes.client.KubernetesClientBuilder;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginLogger;

/**
 * @author Aventix created at: 20.11.2022
 */
public class Mc8sPlugin extends Plugin {
  private static Mc8sPlugin instance = null;
  private static Injector injector = null;

  public static Mc8sPlugin getInstance() {
    return instance;
  }

  public static Injector getInjector() {
    return injector;
  }

  public void onLoad() {
    Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
    KubernetesClient kubernetesClient = new KubernetesClientBuilder().build();
    instance = this;
    injector =
        Guice.createInjector(
            new AbstractModule() {
              @Override
              protected void configure() {
                bind(KubernetesClient.class).toInstance(kubernetesClient);
                bind(ClassLoader.class)
                    .annotatedWith(Names.named("SearcherClassLoader"))
                    .toInstance(Mc8sPlugin.class.getClassLoader());
                bind(PluginLogger.class).toInstance((PluginLogger) Mc8sPlugin.this.getLogger());
                bind(ProxyServer.class).toInstance(Mc8sPlugin.this.getProxy());
              }
            });

    injector
        .getInstance(ConfigurationRegisterProcedure.class)
        .initialize(this.getClass().getPackage().getName());

    BungeeConfiguration configuration = injector.getInstance(BungeeConfiguration.class);
    /*if (!configuration.getLobbyCommands().isEmpty())
    this.getProxy()
            .getPluginManager()
            .registerCommand(
                    this,
                    new LobbyCommand(
                            configuration, messageHandler, injector.getInstance(ServerRegistry.class)));*/
  }

  public void onEnable() {
    injector
        .getInstance(KubernetesClient.class)
        .resources(Pod.class)
            .inAnyNamespace()
        .watch(injector.getInstance(PodWatcher.class));
    this.getProxy().setReconnectHandler(injector.getInstance(AdvancedReconnectHandler.class));
    this.getProxy()
        .getPluginManager()
        .registerListener(this, injector.getInstance(OnlineUserController.class));
    injector.getInstance(OnlineUserController.class).updatePlayerCount();
  }

  public void onDisable() {}
}
