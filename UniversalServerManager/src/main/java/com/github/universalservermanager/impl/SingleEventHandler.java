package com.github.universalservermanager.impl;

import com.github.universalservermanager.api.events.CancellableEvent;
import com.github.universalservermanager.api.events.Event;
import com.github.universalservermanager.api.events.EventHandler;
import com.github.universalservermanager.api.events.Listener;
import com.github.universalservermanager.api.plugin.Plugin;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleEventHandler {
    @Getter
    @Setter
    private Listener listener;
    @Getter
    @Setter
    private Class<?> listenedEvent;
    @Getter
    @Setter
    private Method listeningMethod;
    @Getter
    @Setter
    private Plugin ownerPlugin;

    public void call(Event event) throws InvocationTargetException, IllegalAccessException {
        if (event instanceof CancellableEvent) {
            if (((CancellableEvent) event).isCancelled()) {
                if (listeningMethod.getAnnotation(EventHandler.class).ignoreIfCancelled()) {
                    return;
                }
            }
        }
        listeningMethod.invoke(listener, event);
    }
}
