package com.github.universalservermanager.api.plugin;

import java.util.function.Function;

public interface Channel {
    void addListener(String channel, Function<?,String> listener);
    void removeListener(String channel, Function<?,String> listener);
    void sendMessage(String channel, String message);
}
