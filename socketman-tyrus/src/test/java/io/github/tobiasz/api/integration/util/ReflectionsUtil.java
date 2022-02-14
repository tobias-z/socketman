package io.github.tobiasz.api.integration.util;

import static io.github.tobiasz.common.util.ReflectionsUtil.getAllInPackage;

import io.github.tobiasz.api.server.Channel;

public class ReflectionsUtil {
    public int getTestChannelAmount() {
        return getAllInPackage(this.getClass().getPackageName() + ".testbeans", Channel.class).size();
    }
}
