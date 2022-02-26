package com.github.universalservermanager.impl;

import com.alibaba.fastjson.JSON;
import com.github.universalservermanager.api.PluginManager;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PluginLoader implements PluginManager {
    protected Map<String, Plugin> pluginMap = new HashMap<>();

    protected List<SingleEventHandler> eventHandlers = new ArrayList<>();
    protected File pluginsFolder;

    public void reload() {
        disablePlugins();
        loadPlugins(pluginsFolder);
        enablePlugins();
    }

    public void disablePlugins() {
        for (String name : pluginMap.keySet()) {
            disablePlugin(name);
        }
    }

    @Override
    public void broadcastMessage(String channel, String message) {
        // TODO: 2022/2/24
    }

    @Override
    public boolean containsPlugin(String name) {
        return pluginMap.containsKey(name);
    }

    @Override
    public Plugin getPlugin(String name) {
        return pluginMap.get(name);
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
        Plugin pluginObject = (Plugin) clazz.getDeclaredConstructor(File.class, PluginManager.class, PluginDescription.class).newInstance(plugin, this, pluginDescription);
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
        System.out.printf("Enabling plugin '%s'...%n", name);
        pluginMap.get(name).onEnable();
    }

    @Override
    public boolean isEnabled(String name) {
        return pluginMap.containsKey(name);
    }

    @Override
    public void disablePlugin(String name) {
        System.out.printf("Disabling plugin '%s'...%n", name);
        pluginMap.get(name).onDisable();
        eventHandlers.removeIf(singleEventHandler ->
                singleEventHandler.getOwnerPlugin().getDescription().getName().equals(name));
    }

    public void enablePlugins() {
        pluginMap.forEach((name, plugin) -> enablePlugin(name));
    }

    @Override
    public void callEvents(Event event) {
        eventHandlers.forEach(handler -> {
            if (handler.getListenedEvent().isAssignableFrom(event.getClass())) {
                try {
                    handler.call(event);
                } catch (InvocationTargetException | IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void registerEvents(Plugin plugin, Listener listener) {
        if (listener == null) return;
        Method[] methods = listener.getClass().getMethods();
        for (Method method : methods) {
            if (method.getAnnotation(EventHandler.class) != null) {
                if (method.getReturnType() == void.class && method.getParameterCount() == 1) {
                    if (Event.class.isAssignableFrom(method.getParameterTypes()[0])) {
                        SingleEventHandler singleEventHandler = new SingleEventHandler();
                        singleEventHandler.setListenedEvent((Class<? extends Event>) method.getParameterTypes()[0]);
                        singleEventHandler.setListener(listener);
                        singleEventHandler.setListeningMethod(method);
                        singleEventHandler.setOwnerPlugin(plugin);
                        eventHandlers.add(singleEventHandler);
                    }
                }
            }
        }
    }
}
