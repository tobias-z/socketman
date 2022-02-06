package io.github.tobiasz.bean;

import io.github.tobiasz.exceptions.ResponseException;
import io.github.tobiasz.server.Channel;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class SocketmanBeans {

    private static SocketmanBeans instance;
    private Map<Class<? extends Channel>, ? extends Channel> beans;

    private SocketmanBeans() {
    }

    public static SocketmanBeans getInstance() {
        if (instance == null) {
            instance = new SocketmanBeans();
        }
        return instance;
    }

    public <T> Channel<T> getBean(Class<T> clazz) {
        // TODO: getBeansByChannelName
        if (!beans.containsKey(clazz)) {
            throw ResponseException.create("An unexpected error happened during beaning: unable to find bean: {}", clazz);
        }
        return (Channel<T>) beans.get(clazz);
    }

    public void initBeans() {
        ConfigurationBuilder config = getConfigurationBuilder();
        Reflections reflections = new Reflections(config);
        beans = reflections.getSubTypesOf(Channel.class)
            .stream()
            .collect(Collectors.toMap(
                aClass -> aClass,
                SocketmanBeans::getClass
            ));
        printBeans();
    }

    private static <T> T getClass(Class<T> aClass) {
        try {
            return aClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            throw ResponseException.create("Unable to bean channel: {}", aClass, e);
        }
    }


    private ConfigurationBuilder getConfigurationBuilder() {
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

    private void printBeans() {
        StringBuilder builder = new StringBuilder();
        builder.append("Beaned channels: \n");
        beans.forEach((aClass, channel) -> {
            builder.append(aClass).append(", ");
        });
        System.out.println(builder);
    }

}
