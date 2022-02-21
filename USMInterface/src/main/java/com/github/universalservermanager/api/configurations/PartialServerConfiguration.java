package com.github.universalservermanager.api.configurations;

import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.List;

public class PartialServerConfiguration {
    @Getter
    @Setter
    private String mainServerHost;
    @Getter
    @Setter
    private String listenHost;
    @Getter
    @Setter
    private List<File> serverManagers;
    @Getter
    @Setter
    private String name;
}
