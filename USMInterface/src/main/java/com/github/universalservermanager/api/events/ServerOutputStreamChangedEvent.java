package com.github.universalservermanager.api.events;

import com.github.universalservermanager.api.SingleServerManager;
import lombok.Getter;


/**
 * called when a new line appears in the output stream of managed server.
 */
public class ServerOutputStreamChangedEvent extends Event{
    @Getter
    protected final SingleServerManager server;
    @Getter
    protected final String output;

    public ServerOutputStreamChangedEvent(SingleServerManager server, String output) {
        this.server = server;
        this.output = output;
    }
}
