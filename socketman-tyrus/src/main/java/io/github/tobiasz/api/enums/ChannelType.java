package io.github.tobiasz.api.enums;

import static java.util.Objects.isNull;

import io.github.tobiasz.api.annotation.ChannelConfig;
import io.github.tobiasz.api.server.Channel;

public enum ChannelType {
    ALL_CHANNEL_TYPES;

    public static String getChannelName(Class<? extends Channel> clazz) {
        ChannelConfig channel = clazz.getAnnotation(ChannelConfig.class);
        if (isNull(channel) || channel.channelName().equals("")) {
            return ChannelType.ALL_CHANNEL_TYPES.toString();
        }
        return channel.channelName();
    }
}
