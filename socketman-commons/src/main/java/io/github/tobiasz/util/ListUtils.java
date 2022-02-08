package io.github.tobiasz.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static <T> List<T> join(List<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

}
