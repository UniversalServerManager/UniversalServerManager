package com.github.universalservermanager.impl;

import com.github.universalservermanager.api.events.Listener;
import com.github.universalservermanager.api.plugin.Plugin;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class SingleEventHandler {
    @Getter @Setter
    private Listener listener;
    @Getter @Setter
    private Class<?> listenedEvent;
    @Getter @Setter
    private Method listeningMethod;
    @Getter @Setter
    private Plugin ownerPlugin;
    public void call(Object event) throws InvocationTargetException, IllegalAccessException {
        listeningMethod.invoke(listener,event);
    }
}
