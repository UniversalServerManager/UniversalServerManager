package com.github.universalservermanager.api.plugin;

import lombok.Getter;
import lombok.Setter;

public class PluginDescription {
    @Getter @Setter
    String name;
    @Getter @Setter
    String version;
    @Getter @Setter
    String[] authors;
    @Getter @Setter
    String mainClass;
}
