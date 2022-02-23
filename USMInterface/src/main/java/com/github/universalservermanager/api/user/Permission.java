package com.github.universalservermanager.api.user;

public class Permission {
    int permission;
    public final static int Admin = 65535;
    public final static int None = 0;
    public final static int Read = 1;
    public final static int Execute = 2;
    public final static int Write = 4;
    public final static int ManageUser = 16384;
    public final static int ManagePlugin = 32768;

    public boolean hasFlag(int flag) {
        return (permission & flag) == flag;
    }

    boolean isAdmin() {
        return hasFlag(Admin);
    }

    public Permission(int permission) {
        this.permission = permission;
    }

    public void setPermission(int permission) {
        this.permission = permission;
    }

    public void setPermission(Permission permission) {
        this.permission = permission.permission;
    }

    public void setFlag(int flag, boolean value) {
        if (value) permission |= flag;
        else permission &= (~flag);
    }
}
