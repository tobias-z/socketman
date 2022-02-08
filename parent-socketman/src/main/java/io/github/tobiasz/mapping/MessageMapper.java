package io.github.tobiasz.mapping;

import com.google.gson.Gson;
import io.github.tobiasz.dto.MessageDto;

public class MessageMapper {
    private static final Gson GSON = new Gson();

    public static MessageDto<?> getMessage(String json) {
        return GSON.fromJson(json, MessageDto.class);
    }

}
