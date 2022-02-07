package io.github.tobiasz.context;

import static io.github.tobiasz.enums.ChannelType.getChannelName;
import static io.github.tobiasz.util.ReflectionsUtil.getClassInstance;
import static io.github.tobiasz.util.ReflectionsUtil.getConfigurationBuilder;
import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

import io.github.tobiasz.server.Channel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.reflections.Reflections;
import org.reflections.util.ConfigurationBuilder;

public class SocketmanContext {

    private static SocketmanContext context;
    private final Map<String, List<Channel>> channelMap;
    private final List<Channel> allChannels;

    private SocketmanContext() {
        this.channelMap = new HashMap<>();
        this.allChannels = new ArrayList<>();
        this.initBeans();
    }

    public static SocketmanContext getContext() {
        if (isNull(context)) {
            context = new SocketmanContext();
        }
        return context;
    }

    /**
     *
     * @param channelName A channelName used by your application
     * @return A list of channels with that name or null if it was not found
     */
    public List<Channel> getChannel(String channelName) {
        if (!channelMap.containsKey(channelName)) {
            return null;
        }
        return channelMap.get(channelName);
    }

    /**
     *
     * @param channelNames any amount of channelNames that you want to have
     * @return A list of channels of all the found channels
     */
    public List<Channel> getChannel(String... channelNames) {
        List<Channel> channels = new ArrayList<>();
        for (String channelName : channelNames) {
            List<Channel> foundChannel = this.getChannel(channelName);
            if (nonNull(foundChannel)) {
                channels.addAll(foundChannel);
            }
        }
        return channels;
    }

    /**
     *
     * @return All registered channels
     */
    public List<Channel> getAllChannels() {
        return this.allChannels;
    }

    private void initBeans() {
        ConfigurationBuilder config = getConfigurationBuilder();
        Reflections reflections = new Reflections(config);
        for (Class<? extends Channel> clazz : reflections.getSubTypesOf(Channel.class)) {
            String channelName = getChannelName(clazz);

            if (!this.channelMap.containsKey(channelName)) {
                this.channelMap.put(channelName, new ArrayList<>());
            }
            List<Channel> channels = this.channelMap.get(channelName);
            Channel channel = getClassInstance(clazz);
            channels.add(channel);
            this.allChannels.add(channel);
        }
        printBeans();
    }


    private void printBeans() {
        StringBuilder builder = new StringBuilder();
        builder.append("Beaned channels: \n");
        this.channelMap.forEach((type, classes) -> {
            builder.append(type).append(": ");
            classes.forEach(aClass -> builder.append(aClass.getClass().getName()).append(", "));
            builder.append("\n");
        });
        System.out.println(builder);
    }

}
