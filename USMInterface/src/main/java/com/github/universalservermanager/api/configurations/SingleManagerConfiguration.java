package com.github.universalservermanager.api.configurations;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class SingleManagerConfiguration {
    @Getter @Setter
    private List<String> enabledPlugins;
    @Setter @Getter
    private String name;
}
