package com.github.universalservermanager.impl;

import com.github.universalservermanager.USMServer;
import com.github.universalservermanager.api.user.AbstractUser;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PartialServer {
    List<WebSocket> allowedConnections=new ArrayList<>();
    Map<String, AbstractUser> key2users;
    WebSocketServer webSocketServer=new WebSocketServer() {
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
            if(!allowedConnections.contains(webSocket)){
                String server=s.substring(0,s.indexOf('\n'));
                String key = s.substring(s.indexOf('\n')+1);
                if(key2users.containsKey(key)){
                    allowedConnections.add(webSocket);
                }
                else{
                    webSocket.close();
                }
            }
            else {

            }
        }

        @Override
        public void onError(WebSocket webSocket, Exception e) {

        }

        @Override
        public void onStart() {

        }
    };
}
