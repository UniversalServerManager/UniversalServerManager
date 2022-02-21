package com.github.universalservermanager.api.events;

import com.github.universalservermanager.api.SingleServerManager;
import com.github.universalservermanager.api.user.AbstractUser;
import lombok.Getter;

public class ServerBeStoppedEvent extends CancellableEvent {
    @Getter
    protected final AbstractUser user;
    @Getter
    protected final SingleServerManager server;
    @Getter
    protected final boolean force;

    public ServerBeStoppedEvent(AbstractUser user, SingleServerManager server, boolean force) {
        this.user = user;
        this.server = server;
        this.force = force;
    }

}
