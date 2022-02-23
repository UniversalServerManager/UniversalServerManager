package com.github.universalservermanager.impl.remote;

import com.github.universalservermanager.USMServer;
import com.github.universalservermanager.api.user.AbstractUser;
import com.github.universalservermanager.api.user.Permission;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.ExecutionException;

public class RemoteUser extends AbstractUser {
    String accessKey;
    String authHost;

    public RemoteUser(String name, String accessKey) {
        this.name = name;
        this.accessKey = accessKey;
    }

    @Override
    public Permission getPermission(String server) {
        try {
            HttpResponse<String> res = USMServer.getInstance().getClient().sendAsync(
                    HttpRequest.newBuilder()
                            .GET()
                            .uri(new URI(authHost+"/api/get_permission/"+name))
                            .build(),
                    HttpResponse.BodyHandlers.ofString()).get();
            if (res.statusCode() == 200) {
                return new Permission(Integer.getInteger(res.body()));
            } else {
                return new Permission(Permission.None);
            }
        } catch (URISyntaxException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        return new Permission(Permission.None);
    }

    @Override
    public void setPermission(String server, Permission permission) {

    }
}
