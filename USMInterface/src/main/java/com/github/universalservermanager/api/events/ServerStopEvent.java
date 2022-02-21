package com.github.universalservermanager.api.events;

import com.github.universalservermanager.api.SingleServerManager;
import lombok.Getter;

public class ServerStopEvent extends Event{
    @Getter
    final protected SingleServerManager server;
    @Getter
    final protected int exitCode;

    public ServerStopEvent(SingleServerManager server, int exitCode) {
        this.server = server;
        this.exitCode = exitCode;
    }
}
