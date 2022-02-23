package com.github.universalservermanager.impl.remote;

import com.github.universalservermanager.api.SingleServerManager;
import com.github.universalservermanager.api.user.AbstractUser;
import lombok.Getter;

public class RemoteSingleServerManager implements SingleServerManager {
    @Getter
    String name;
    @Override
    public Process getProcess() {
        return null;
    }

    @Override
    public boolean isAlive() {
        return false;
    }

    @Override
    public String getProgramName() {
        return null;
    }

    @Override
    public void start(AbstractUser user) {

    }

    @Override
    public void stop(AbstractUser user, boolean force) {

    }

    @Override
    public void start() {

    }

    @Override
    public void stop(boolean force) {

    }

    @Override
    public void inputCommand(String command) {

    }

    @Override
    public String getStopCommand() {
        return null;
    }

    @Override
    public void setStopCommand(String command) {
    }
}
