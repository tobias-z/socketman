package io.github.tobiasz.integration.util;

import io.github.tobiasz.server.Channel;
import java.util.Set;
import org.reflections.Reflections;

public class ReflectionsUtil {
    public int getTestChannelAmount() {
        return getAllInPackage(this.getClass().getPackageName() + ".testbeans", Channel.class).size();
    }

    public <T> Set<Class<? extends T>> getAllInPackage(String packageName, Class<T> clazz) {
        Reflections reflections = new Reflections(packageName);
        return reflections.getSubTypesOf(clazz);
    }
}
