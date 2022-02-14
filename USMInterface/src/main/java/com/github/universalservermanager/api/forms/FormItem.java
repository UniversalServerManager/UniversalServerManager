package com.github.universalservermanager.api.forms;

public class FormItem {
    FormItemType type;
    String tips;
    String name;

    public FormItemType getType() {
        return type;
    }

    public void setType(FormItemType type) {
        this.type = type;
    }

    public String getTips() {
        return tips;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTips(String tips) {
        this.tips = tips;
    }

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
