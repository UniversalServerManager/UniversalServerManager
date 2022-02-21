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
    protected File pluginsFolder;

    public void reload() {
        disablePlugins();
        loadPlugins(pluginsFolder);
        enablePlugins();
    }

    public void disablePlugins() {
        pluginMap.forEach((name, plugin) -> plugin.onDisable());
    }

    public void loadPlugin(File plugin) throws IOException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{new URL("file:" + plugin.getAbsolutePath())});

        InputStream jsonFile = urlClassLoader.getResourceAsStream("plugin.json");
        if (jsonFile == null) {
            throw new FileNotFoundException("File 'plugin.json' is not found.");
        }
        PluginDescription pluginDescription = JSON.parseObject(jsonFile, PluginDescription.class);
        Class<?> clazz = urlClassLoader.loadClass(pluginDescription.getMainClass());
        if (pluginMap.containsKey(pluginDescription.getName()))
            return;
        Plugin pluginObject = (Plugin) clazz.getDeclaredConstructor(File.class, PluginDescription.class).newInstance(plugin, pluginDescription);
        pluginMap.put(pluginDescription.getName(), pluginObject);
        pluginObject.onLoad();
    }

    public void loadPlugins(File directory) {
        pluginsFolder = directory;
        File[] jars = directory.listFiles((dir, name) -> name.endsWith(".jar"));
        if (jars == null) return;
        for (File jar : jars) {
            System.out.printf("Loading plugin '%s' ...%n", jar.getName());
            try {
                loadPlugin(jar);
            } catch (IOException | ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public void enablePlugin(String name) {
        pluginMap.get(name).onEnable();
    }

    public void enablePlugins() {
        pluginMap.forEach((name, plugin) -> enablePlugin(name));
    }

    public void callEvents(Event event) {
        eventHandlerMap.forEach((clazz, handler) -> {
            if (clazz.isAssignableFrom(event.getClass())) {
                try {
                    handler.call(event);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void registerEvents(Plugin plugin, Listener listener) {
        if (listener == null) return;
        Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) != null) {
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
