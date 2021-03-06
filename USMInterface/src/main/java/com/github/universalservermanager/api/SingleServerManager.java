package com.github.universalservermanager.api;

import com.github.universalservermanager.api.user.AbstractUser;

public interface SingleServerManager {
        Process getProcess();
        boolean isAlive();
        String getName();
        String getProgramName();
        void start(AbstractUser user);
        void stop(AbstractUser user, boolean force);
        void start();
        void stop(boolean force);
        void inputCommand(String command);
        String getStopCommand();
        void setStopCommand(String command);

    void setProgramName(String programName);

    java.util.List<org.java_websocket.WebSocket> getWebSockets();

    java.io.File getWorkDir();

    com.github.universalservermanager.api.configurations.SingleManagerConfiguration getConfiguration();
}
