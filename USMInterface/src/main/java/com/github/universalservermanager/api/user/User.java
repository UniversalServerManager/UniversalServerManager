package com.github.universalservermanager.api.user;

import java.util.HashMap;
import java.util.Map;

public class User extends AbstractUser {
    protected Map<String, Permission> permissionMap = new HashMap<>();

    @Override
    public Permission getPermission(String server) {
        return permissionMap.get(server);
    }

    @Override
    public void setPermission(String server, Permission permission) {
        permissionMap.get(server).setPermission(permission);
    }
}
