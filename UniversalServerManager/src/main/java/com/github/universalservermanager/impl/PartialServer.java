package com.github.universalservermanager.impl;

import com.alibaba.fastjson.JSON;
import com.github.universalservermanager.USMServer;
import com.github.universalservermanager.api.configurations.PartialServerConfiguration;
import com.github.universalservermanager.api.user.AbstractUser;
import com.github.universalservermanager.impl.remote.RemoteUser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpsServer;
import lombok.Getter;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class PartialServer implements com.github.universalservermanager.api.PartialServer {
    List<WebSocket> allowedConnections = new ArrayList<>();
    Map<String, AbstractUser> key2users;
    @Getter
    WebSocketServer webSocketServer = new WebSocketServer() {
        @Override
        public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
            USMServer.getInstance().getThreadPool().submit(() -> {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
                if (!webSocket.isOpen()) return;
                if (!allowedConnections.contains(webSocket)) {
                    webSocket.close();
                }
            });
        }

        @Override
        public void onClose(WebSocket webSocket, int i, String s, boolean b) {
            allowedConnections.remove(webSocket);
        }

        @Override
        public void onMessage(WebSocket webSocket, String s) {
            if (!allowedConnections.contains(webSocket)) {
                String server = s.substring(0, s.indexOf('\n'));
                String key = s.substring(s.indexOf('\n') + 1);
                if (key2users.containsKey(key)) {
                    allowedConnections.add(webSocket);
                } else {
                    webSocket.close();
                }
            } else {

            }
        }

        @Override
        public void onError(WebSocket webSocket, Exception e) {

        }

        @Override
        public void onStart() {

        }
    };
    @Getter
    PartialServerConfiguration configuration = null;
    @Getter
    HttpServer httpServer;
    @Getter
    HttpsServer httpsServer;
    @Getter
    Map<String,SingleServerManager> serverManagerMap=new HashMap<>();

    public PartialServer() {
        try {
            httpServer=HttpServer.create(InetSocketAddress.createUnresolved(configuration.getListenHost(),80),-1);
            httpServer.createContext("/interface/set_key_enabled", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    String key = exchange.getRequestHeaders().get("key").get(0);
                    String user = exchange.getRequestHeaders().get("user").get(0);
                    key2users.put(key,new RemoteUser(user,key));
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        File confFile=new File(USMServer.getInstance().getJarLocation().getParentFile(),"config.json");
        if(confFile.exists()) {
            try {
                configuration = JSON.parseObject(new FileInputStream(confFile).readAllBytes(), PartialServerConfiguration.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                configuration=new PartialServerConfiguration();
                JSON.writeJSONString(new FileOutputStream(confFile),
                        Charset.defaultCharset(),
                        configuration);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if(!configuration.checkNull()){
            Thread.currentThread().stop();
        }
        HttpClient client = HttpClient.newHttpClient();
        try {
            String body = client.sendAsync(HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("token=" + configuration.getToken())).uri(new URI("")).build(), HttpResponse.BodyHandlers.ofString()).get().body();
        } catch (URISyntaxException | ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
