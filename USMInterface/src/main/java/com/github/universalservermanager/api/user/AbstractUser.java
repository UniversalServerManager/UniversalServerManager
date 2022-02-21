package com.github.universalservermanager.api.user;

import lombok.Getter;
import lombok.Setter;

public abstract class AbstractUser {
    public abstract Permission getPermission(String server);
    public abstract void setPermission(String server, Permission permission);
    @Getter
    @Setter
    protected boolean admin;

}
