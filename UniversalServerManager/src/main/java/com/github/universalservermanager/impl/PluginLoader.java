package com.github.universalservermanager.impl;

import com.alibaba.fastjson.JSON;
import com.github.universalservermanager.api.events.Event;
import com.github.universalservermanager.api.events.EventHandler;
import com.github.universalservermanager.api.events.Listener;
import com.github.universalservermanager.api.plugin.Plugin;
import com.github.universalservermanager.api.plugin.PluginDescription;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;

public class PluginLoader {
    protected Map<String, Plugin> pluginMap = new HashMap<>();
    protected Map<Class<? extends Event>, SingleEventHandler> eventHandlerMap = new HashMap<>();

    public void loadPlugin(File plugin) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:" + plugin.getPath())});

        InputStream jsonFile = urlClassLoader.getResourceAsStream("plugin.json");
        if (jsonFile == null) {
            throw new FileNotFoundException("File 'plugin.json' is not found.");
        }
        PluginDescription pluginDescription = JSON.parseObject(jsonFile, PluginDescription.class);
        Class<?> clazz = urlClassLoader.loadClass(pluginDescription.getMainClass());
        if (pluginMap.containsKey(pluginDescription.getName()))
            return;
        Plugin pluginObject = (Plugin) clazz.getDeclaredConstructor(File.class, PluginDescription.class).newInstance(plugin, pluginDescription);
        pluginObject.onLoad();
        pluginMap.put(pluginDescription.getName(), pluginObject);
    }

    public void loadPlugins(File directory) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        for (File jar : jars) {
            System.out.printf("Loading plugin '%s' ...%n", jar.getName());
            try {
                loadPlugin(jar);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public void enablePlugin(String name) {
        pluginMap.get(name).onEnable();
    }

    public void enablePlugins() {
        pluginMap.forEach((name, plugin) -> {
            enablePlugin(name);
        });
    }

    public void registerEvents(Plugin plugin, Listener listener) {
        Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) == null) {
                if (method.getReturnType() == void.class && method.getParameterCount() == 1) {
                    if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        SingleEventHandler singleEventHandler = new SingleEventHandler();
                        singleEventHandler.setListenedEvent(method.getParameterTypes()[0]);
                        singleEventHandler.setListener(listener);
                        singleEventHandler.setListeningMethod(method);
                        singleEventHandler.setOwnerPlugin(plugin);
                        eventHandlerMap.put((Class<? extends Event>) method.getParameterTypes()[0], singleEventHandler);
                    }
                }
            }
        }
    }
}
