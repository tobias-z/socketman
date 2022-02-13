package io.github.tobiasz.util;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Console {

    public static void print(String message, Object... args) {
        Queue<Object> queue = new LinkedList<>(List.of(args));
        System.out.println(getFormattedMessage(message, queue));
        if (!queue.isEmpty()) {
            Object arg = queue.remove();
            if (arg instanceof Exception) {
                ((Exception) arg).printStackTrace();
                System.out.println(((Exception) arg).getMessage());
            }
        }
    }

    public static String getFormattedMessage(String message, Queue<Object> queue) {
        while (message.contains("{}")) {
            if (queue.isEmpty()) {
                break;
            }
            message = message.replaceFirst("\\{}", String.valueOf(queue.remove()));
        }
        return message;
    }
}
