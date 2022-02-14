package com.github.universalservermanager.api;

import com.github.universalservermanager.api.plugin.Plugin;

import java.io.File;

public interface PluginManager {
    void broadcastMessage(String channel, String message);
    boolean containsPlugin(String name);
    Plugin getPlugin(String name);
    void loadPlugin(File plugin);
    void enablePlugin(String name);
    boolean isEnabled(String name);
    void disablePlugin(String name);

}
