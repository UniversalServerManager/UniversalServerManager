package com.github.universalservermanager;

import com.github.universalservermanager.api.user.AbstractUser;
import com.github.universalservermanager.impl.PluginLoader;
import com.sun.net.httpserver.HttpServer;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.http.HttpClient;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class USMServer {
    @Getter
    @Setter
    StartupSettings startupSettings;
    static USMServer Instance;
    @Getter
    PluginLoader pluginLoader;
    @Getter
    File jarLocation;
    @Getter
    ExecutorService threadPool=Executors.newCachedThreadPool();
    @Getter
    HttpClient client=HttpClient.newHttpClient();
    @Getter
    boolean secure=false;
    @Getter
    HttpServer server;

    public static USMServer getInstance() {
        return Instance;
    }

    public AbstractUser getUser(String name) {
        return null;
    }

    public boolean removeUser(String name) {
        return false;
    }

    public boolean addUser(String name) {
        return false;
    }

    public void reload(){
        pluginLoader.reload();
    }

    USMServer(StartupSettings startupSettings) {
        this.startupSettings = startupSettings;
        pluginLoader = new PluginLoader();
        try {
            jarLocation = new File(java.net.URLDecoder.decode(this.getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        File pluginsFolder = new File(jarLocation, "plugins/");
        if (!pluginsFolder.exists()) pluginsFolder.mkdir();
        pluginLoader.loadPlugins(pluginsFolder);
        // setup servers
        switch (startupSettings.getType()){
            case PARTIAL -> {

            }
        }
    }
}
