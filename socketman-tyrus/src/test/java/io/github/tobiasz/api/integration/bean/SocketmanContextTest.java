package io.github.tobiasz.api.integration.bean;

import static org.assertj.core.api.Assertions.assertThat;

import io.github.tobiasz.test.annotation.SocketmanTest;
import io.github.tobiasz.api.context.SocketmanContext;
import io.github.tobiasz.api.integration.util.ReflectionsUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@SocketmanTest
class SocketmanContextTest {

    private final SocketmanContext context = SocketmanContext.getContext();
    private final ReflectionsUtil reflectionsUtil = new ReflectionsUtil();

    @Test
    @DisplayName("when getAllChannels is called then correct amount of beans are returned")
    void whenGetAllChannelsIsCalledThenCorrectAmountBeansAreReturned() throws Exception {
        int expected = reflectionsUtil.getTestChannelAmount();
        int actual = context.getAllChannels().size();
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @DisplayName("hello world")
    void helloWorld() throws Exception {
        System.out.println("hello");
    }

}