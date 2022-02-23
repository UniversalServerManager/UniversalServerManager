package com.github.universalservermanager.impl.remote;

import com.github.universalservermanager.api.PartialServer;
import com.github.universalservermanager.api.configurations.PartialServerConfiguration;
import com.github.universalservermanager.impl.SingleServerManager;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import lombok.Getter;
import org.java_websocket.server.WebSocketServer;

import java.util.Map;

public class RemotePartialServer implements PartialServer {
    @Getter
    PartialServerConfiguration configuration;
    public RemotePartialServer(PartialServerConfiguration configuration){
        this.configuration=configuration;
    }
    @Override
    public WebSocketServer getWebSocketServer() {
        return null;
    }

    @Override
    public HttpServer getHttpServer() {
        return null;
    }

    @Override
    public HttpsServer getHttpsServer() {
        return null;
    }

    @Override
    public Map<String, SingleServerManager> getServerManagerMap() {
        return null;
    }
}
