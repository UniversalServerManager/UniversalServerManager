package com.github.universalservermanager.api.configurations;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class MainServerConfiguration extends Configuration {
    @Getter
    @Setter
    @NonNull
    private String listenHost;
    @Getter
    @Setter
    @NonNull
    private String token;
    @Getter
    @Setter
    private List<PartialServer> partialServers=new ArrayList<>();
    public class PartialServer extends Configuration {
        @Getter
        @Setter
        @NonNull
        private String name;
        @Getter
        @Setter
        @NonNull
        private String host;
    }
}
