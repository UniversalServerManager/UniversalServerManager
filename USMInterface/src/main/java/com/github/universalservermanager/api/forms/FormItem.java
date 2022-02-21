package com.github.universalservermanager.api.forms;

import lombok.Getter;
import lombok.Setter;

public class FormItem {
    @Getter @Setter
    FormItemType type;
    @Getter @Setter
    String tips;
    @Getter @Setter
    String name;

    public FormItem(FormItemType type, String name) {
        this(type, name, "");
    }

    public FormItem(FormItemType type, String name, String tips){
        this.type=type;
        this.tips=tips;
        this.name=name;
    }

    public String toHtmlDiv(){
        return "";
        //TODO
    }
}
