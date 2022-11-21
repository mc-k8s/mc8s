package com.mc8s.bridge.event;

import com.mc8s.bridge.Mc8sBridgeController;
import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * @author Aventix created at: 21.11.2022
 */
@Singleton
public class PodWatcher implements Watcher<Pod> {
    private final Mc8sBridgeController bridgeController;

    @Inject
    public PodWatcher(Mc8sBridgeController bridgeController) {
        this.bridgeController = bridgeController;
    }

    @Override
    public void eventReceived(Action action, Pod resource) {
        System.out.println(action.name().toUpperCase());
        System.out.println(resource.getStatus());
        System.out.println(resource.getFullResourceName());
        System.out.println(resource.getSpec().getContainers().size());
        System.out.println(resource.getSpec().getContainers().get(0).getPorts().get(0));
    }

    @Override
    public void onClose(WatcherException cause) {

    }
}
