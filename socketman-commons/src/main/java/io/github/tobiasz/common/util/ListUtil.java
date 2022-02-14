package io.github.tobiasz.common.util;

import java.util.ArrayList;
import java.util.List;

public class ListUtil {

    public static <T> List<T> join(List<T>... lists) {
        List<T> newList = new ArrayList<>();
        for (List<T> list : lists) {
            newList.addAll(list);
        }
        return newList;
    }

}
