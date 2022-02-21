package com.github.universalservermanager.api;

import com.github.universalservermanager.api.user.AbstractUser;

public abstract class USM {
    public abstract AbstractUser getUser(String name);
    public abstract boolean removeUser(String name);
    public abstract boolean addUser(String name);

    
}
