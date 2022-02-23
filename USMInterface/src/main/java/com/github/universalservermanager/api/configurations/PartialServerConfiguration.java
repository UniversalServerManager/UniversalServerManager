package com.github.universalservermanager.api.configurations;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PartialServerConfiguration extends Configuration {
    @Getter
    @Setter
    @NonNull
    private String mainServerHost;
    @Getter
    @Setter
    @NonNull
    private String listenHost;
    @Getter
    @Setter
    private List<File> serverManagers=new ArrayList<>();
    @Getter
    @Setter
    @NonNull
    private String name;
    @Getter
    @Setter
    @NonNull
    private String token;
}
