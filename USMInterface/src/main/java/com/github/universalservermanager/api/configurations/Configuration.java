package com.github.universalservermanager.api.configurations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;

public abstract class Configuration {
    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @interface NonNull {

    }
    public boolean checkNull() {
        for (Field field : this.getClass().getFields()) {
            if (field.getAnnotation(NonNull.class) != null) {
                try {
                    if (field.get(this) == null) return false;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return true;
    }
}
