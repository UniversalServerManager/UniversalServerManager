package com.github.universalservermanager.api.events;

import com.github.universalservermanager.api.SingleServerManager;
import com.github.universalservermanager.api.user.AbstractUser;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class ServerStartEvent extends CancellableEvent {
    @Getter
    protected final AbstractUser user;
    @Getter
    protected final SingleServerManager server;
    @Getter
    protected final List<String> arguments = new ArrayList<>();

    public ServerStartEvent(AbstractUser user, SingleServerManager server) {
        this.user = user;
        this.server = server;
    }
}
