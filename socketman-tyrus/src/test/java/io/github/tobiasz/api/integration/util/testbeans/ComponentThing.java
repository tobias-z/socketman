package io.github.tobiasz.api.integration.util.testbeans;

import io.github.tobiasz.api.annotation.Component;
import java.util.Map;

@Component
public class ComponentThing {

    private final Map<String, String> map;

    public ComponentThing(Map<String, String> map) {
        this.map = map;
    }
}