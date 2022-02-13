package io.github.tobiasz.mapping;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import io.github.tobiasz.dto.MessageDto;
import io.github.tobiasz.exceptions.ClientResponseException;

public class MessageMapper {

    private static final Gson GSON = new Gson();

    public static MessageDto<?> getMessage(String json) {
        try {
            return GSON.fromJson(json, MessageDto.class);
        } catch (JsonSyntaxException e) {
            throw ClientResponseException.create(
                "Unable to send message '{}' because it did not follow the correct format. Expected format: '{}'",
                json,
                GSON.toJson(new MessageDto<>("exampleChannel", "example message"))
            );
        }
    }

}
