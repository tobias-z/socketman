package io.github.tobiasz.integration.bean;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.tobiasz.bean.SocketmanBeans;
import io.github.tobiasz.integration.util.ReflectionsUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class SocketmanBeansTest {

    SocketmanBeans beans = SocketmanBeans.getInstance();
    ReflectionsUtil reflectionsUtil = new ReflectionsUtil();

    @BeforeEach
    void setUp() {
        beans.initBeans();
    }

    @Test
    @DisplayName("when getAllChannels is called then correct amount of beans are returned")
    void whenGetAllChannelsIsCalledThenCorrectAmountBeansAreReturned() throws Exception {
        int expected = reflectionsUtil.getTestChannelAmount();
        int actual = beans.getAllChannels().size();
        assertThat(actual).isEqualTo(expected);
    }

}