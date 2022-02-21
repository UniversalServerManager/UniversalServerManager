package com.github.universalservermanager.api.plugin;

import com.alibaba.fastjson.JSON;

import java.io.File;

public interface Plugin {
    void onEnable();
    void onDisable();
    void onLoad();
    JSON getConfig() throws Exception;
    PluginDescription getDescription();
    File getDataFolder();
    File getPluginFile();
}
