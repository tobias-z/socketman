package io.github.tobiasz.api.context;

@FunctionalInterface
public interface BeanCreation {
    void create() throws NoSuchMethodException;
}
