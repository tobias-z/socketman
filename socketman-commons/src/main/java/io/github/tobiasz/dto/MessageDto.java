package io.github.tobiasz.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@Getter
@ToString
public class MessageDto<T> {
    private final String channelName;
    private final T message;
}
