package com.github.universalservermanager.api.configurations;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class SingleManagerConfiguration extends Configuration {
    @Getter
    @Setter
    private List<String> enabledPlugins=new ArrayList<>();
    @Setter
    @Getter
    @NonNull
    private String name;

}
