package io.github.tobiasz.bean;

import io.github.tobiasz.annotation.ChannelType;
import io.github.tobiasz.enums.ChannelName;
import io.github.tobiasz.exceptions.ResponseException;
import io.github.tobiasz.server.Channel;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import org.reflections.Reflections;
import org.reflections.scanners.Scanners;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

public class SocketmanBeans {

    private static SocketmanBeans instance;
    private Map<String, List<Channel>> channelMap;

    private SocketmanBeans() {
    }

    public static SocketmanBeans getInstance() {
        if (instance == null) {
            instance = new SocketmanBeans();
        }
        return instance;
    }

    /**
     *
     * @param channelType A channelType used by your application
     * @return A list of channels with that name or null if it was not found
     */
    public List<Channel> getChannel(String channelType) {
        if (!channelMap.containsKey(channelType)) {
            return null;
        }
        return channelMap.get(channelType);
    }

    public List<Channel> getAllChannels() {
        // TODO: create allChannelList on init
        List<Channel> channelList = new ArrayList<>();
        channelMap.forEach((s, channels) -> channelList.addAll(channels));
        return channelList;
    }

    public void initBeans() {
        ConfigurationBuilder config = getConfigurationBuilder();
        Reflections reflections = new Reflections(config);
        channelMap = new HashMap<>();
        for (Class<? extends Channel> clazz : reflections.getSubTypesOf(Channel.class)) {
            String channelType = getChannelType(clazz);

            if (!channelMap.containsKey(channelType)) {
                channelMap.put(channelType, new ArrayList<>());
            }
            List<Channel> channels = channelMap.get(channelType);
            channels.add(getClass(clazz));
        }
        printBeans();
    }

    private String getChannelType(Class<? extends Channel> clazz) {
        ChannelType channel = clazz.getAnnotation(ChannelType.class);
        if (channel == null || channel.channelType().equals("")) {
            return ChannelName.ALL_CHANNEL_TYPES.toString();
        }
        return channel.channelType();
    }

    private <T> T getClass(Class<T> aClass) {
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
        channelMap.forEach((type, classes) -> {
            builder.append(type).append(": ");
            classes.forEach(aClass -> builder.append(aClass.getClass().getName()).append(", "));
            builder.append("\n");
        });
        System.out.println(builder);
    }

}
