package com.github.universalservermanager.impl;

import com.alibaba.fastjson.JSON;
import com.github.universalservermanager.USMServer;
import com.github.universalservermanager.api.configurations.SingleManagerConfiguration;
import com.github.universalservermanager.api.events.ServerBeStoppedEvent;
import com.github.universalservermanager.api.events.ServerOutputStreamChangedEvent;
import com.github.universalservermanager.api.events.ServerStartEvent;
import com.github.universalservermanager.api.events.ServerStopEvent;
import com.github.universalservermanager.api.user.AbstractUser;
import lombok.Getter;
import lombok.Setter;
import org.java_websocket.WebSocket;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SingleServerManager implements com.github.universalservermanager.api.SingleServerManager {
    @Getter
    List<WebSocket> webSockets = new ArrayList<>();
    @Getter
    @Setter
    String name;
    @Getter
    @Setter
    String stopCommand;
    @Getter
    @Setter
    String programName;
    @Getter
    Process process;
    @Getter
    final File workDir;
    @Getter
    @Setter
    SingleManagerConfiguration configuration;
    static String[] defaultStopCommands=new String[]{"stop","exit","end","exit()",".exit"};

    void SendCache(WebSocket webSocket){
    }

    private void outputListener() {
        if (process == null) return;
        if (!process.isAlive()) return;
        try {
            String newLine = process.inputReader().readLine();
            webSockets.forEach(websocket->websocket.send(newLine));
            USMServer.getInstance().getPluginLoader().callEvents(new ServerOutputStreamChangedEvent(this, newLine));
            if (!process.isAlive()) {
                ServerStopEvent event = new ServerStopEvent(this, process.exitValue());
                USMServer.getInstance().getPluginLoader().callEvents(event);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public SingleServerManager(File workDir) {
        this.workDir = workDir;
        File usmDir = new File(this.workDir, ".usm/");
        if (!usmDir.exists()) {
            usmDir.mkdir();
        }
        File configFile = new File(usmDir, "config.json");
        if (configFile.exists()) {
            try {
                configuration = JSON.parseObject(new FileInputStream(configFile).readAllBytes(), SingleManagerConfiguration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                configFile.createNewFile();
                new FileOutputStream(configFile).write(JSON.toJSONBytes(configuration));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        USMServer.getInstance().getThreadPool().submit((Runnable) () -> {
            while (true) {
                outputListener();
            }
        });
    }

    @Override
    public void start() {
        try {
            process = Runtime.getRuntime().exec(programName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(AbstractUser user) {
        if (process != null && process.isAlive()) return;
        ServerStartEvent event = new ServerStartEvent(user, this);
        USMServer.getInstance().getPluginLoader().callEvents(event);
        if (event.isCancelled()) return;
        event.getArguments().add(0, programName);
        try {
            process = Runtime.getRuntime().exec((String[]) event.getArguments().toArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop(boolean force) {
        if(force){
            process.destroy();
        }
        else {
            try {
                if (stopCommand != null && !stopCommand.equals("")) {
                    process.outputWriter().write(stopCommand + '\n');
                } else {
                    for (String command : defaultStopCommands) {
                        process.outputWriter().write(command + '\n');
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void stop(AbstractUser user, boolean force) {
        ServerBeStoppedEvent event=new ServerBeStoppedEvent(user,this,force);
        if(event.isCancelled())return;
        stop(force);
    }

    @Override
    public void inputCommand(String command) {
        try {
            process.outputWriter().write(command+'\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean isAlive() {
        if (process == null) return false;
        return process.isAlive();
    }
}
