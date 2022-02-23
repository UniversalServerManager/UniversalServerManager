package com.github.universalservermanager.api;

public interface PartialServer {
    org.java_websocket.server.WebSocketServer getWebSocketServer();

    com.github.universalservermanager.api.configurations.PartialServerConfiguration getConfiguration();

    com.sun.net.httpserver.HttpServer getHttpServer();

    com.sun.net.httpserver.HttpsServer getHttpsServer();

    java.util.Map<String, ? extends SingleServerManager> getServerManagerMap();
}
