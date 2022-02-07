package io.github.tobiasz.util;

import io.github.tobiasz.exceptions.ResponseException;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class ReflectionsUtil {

    public static ConfigurationBuilder getConfigurationBuilder() {
        Collection<URL> allPackagePrefixes = Arrays.stream(Package.getPackages())
            .map(Package::getName)
            .map(s -> s.split("\\.")[0])
            .distinct()
            .map(ClasspathHelper::forPackage)
            .reduce((c1, c2) -> {
                Collection<URL> c3 = new HashSet<>();
                c3.addAll(c1);
                c3.addAll(c2);
                return c3;
            }).get();
        return new ConfigurationBuilder().addUrls(allPackagePrefixes)
            .addScanners(Scanners.SubTypes);
    }

    public static <T> T getClassInstance(Class<T> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw ResponseException.create("Unable to create instance of class: {}", aClass, e);
        }
    }

}
