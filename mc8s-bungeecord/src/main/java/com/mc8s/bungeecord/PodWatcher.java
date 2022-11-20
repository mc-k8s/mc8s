package com.mc8s.bungeecord;

import io.fabric8.kubernetes.api.model.Pod;
import io.fabric8.kubernetes.client.Watcher;
import io.fabric8.kubernetes.client.WatcherException;

/**
 * @author Aventix created at: 20.11.2022
 */
public class PodWatcher implements Watcher<Pod> {
    @Override
    public void eventReceived(Action action, Pod resource) {
        System.out.println(action + " " + resource);
    }

    @Override
    public void onClose(WatcherException cause) {

    }
}
