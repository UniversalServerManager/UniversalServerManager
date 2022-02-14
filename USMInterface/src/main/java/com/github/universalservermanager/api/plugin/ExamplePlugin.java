package com.github.universalservermanager.api.plugin;

import com.alibaba.fastjson.JSON;

import java.io.*;

public class ExamplePlugin implements Plugin {
    final File pluginFile;
    final PluginDescription description;
    JSON config;

    public ExamplePlugin(File plugin, PluginDescription description) {
        pluginFile = plugin;
        this.description = description;
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
            byte[] content = new byte[(int) file.length()];
            FileInputStream stream = new FileInputStream(file);
            stream.read(content);
            stream.close();
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
    public boolean signed() {
        return false;
    }

    @Override
    public boolean installKeyChecked() {
        return false;
    }

    @Override
    public File getDataFolder() {
        return new File(pluginFile.getParentFile(), getDescription().name + '/');
    }

    @Override
    public File getPluginFile() {
        return pluginFile;
    }
}
