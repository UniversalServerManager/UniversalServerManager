package com.github.universalservermanager.api.forms;

public interface UserForm {
    boolean addItem(FormItem formItem);
    boolean removeItem(Object formItem);
    boolean containsItem(Object formItem);
    boolean getItem(String name);
    String getHtml();
}
