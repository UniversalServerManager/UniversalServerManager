package com.github.universalservermanager.impl;

import com.github.universalservermanager.USMServer;
import com.github.universalservermanager.api.PartialServer;
import com.github.universalservermanager.api.configurations.Configuration;
import com.github.universalservermanager.api.configurations.MainServerConfiguration;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class MainServer {
    MainServerConfiguration configuration;
    Map<String, PartialServer> partialServerMap=new HashMap<>();
    public MainServer(Configuration configuration) {
        this.configuration = (MainServerConfiguration) configuration;
        for (MainServerConfiguration.PartialServer partialServer : this.configuration.getPartialServers()) {
            try {
                HttpResponse<String> response = USMServer.getInstance().getClient().send(HttpRequest.newBuilder()
                        .GET()
                        .uri(new URI(partialServer.getHost() + "/api/status"))
                        .build(), HttpResponse.BodyHandlers.ofString(StandardCharsets.UTF_8));
                if(response.statusCode()!=200) {
                    throw new IOException();
                }

            } catch (IOException | InterruptedException e) {
                System.out.println("Unable to connect the partial server.");
                System.out.println("Please make sure that the server is running and reachable.");
                System.out.println("This may be a temporary error and we will try to reconnect later.");
            } catch (URISyntaxException e) {
                System.out.println("Host of partial server [" + partialServer.getName() + "] is invalid, ignored.");
            }
        }
    }
}
