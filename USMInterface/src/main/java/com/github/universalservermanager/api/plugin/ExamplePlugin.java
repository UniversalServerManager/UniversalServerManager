package com.github.universalservermanager.api.plugin;

import com.alibaba.fastjson.JSON;
import com.github.universalservermanager.api.PluginManager;

import java.io.*;

public class ExamplePlugin implements Plugin {
    final File pluginFile;
    final PluginDescription description;
    final PluginManager pluginManager;
    JSON config;

    public ExamplePlugin(File plugin, PluginManager pluginManager, PluginDescription description) {
        pluginFile = plugin;
        this.description = description;
        this.pluginManager = pluginManager;
    }

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @Override
    public void onLoad() {

    }

    @Override
    public JSON getConfig() throws Exception {
        if (config != null)
            return config;
        File file = new File(getDataFolder(), "config.json");
        try {
            byte[] content = new FileInputStream(file).readAllBytes();
            config = (JSON) JSON.parse(content);
        } catch (FileNotFoundException e) {
            InputStream stream = this.getClass().getResourceAsStream("config.json");
            if (stream == null) {
                throw new Exception("config.json is not found.");
            }
            byte[] content = stream.readAllBytes();
            stream.close();
            config = (JSON) JSON.parse(content);
        }
        return config;
    }

    @Override
    public PluginDescription getDescription() {
        return description;
    }

    @Override
    public File getDataFolder() {
        return new File(pluginFile.getParentFile(), getDescription().name + '/');
    }

    @Override
    public File getPluginFile() {
        return pluginFile;
    }

    @Override
    public PluginManager getPluginManager() {
        return pluginManager;
    }
}
