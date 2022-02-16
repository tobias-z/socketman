package io.github.tobiasz.api.context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InProcessContext {

    private final Map<Class<?>, List<BeanCreation>> inProcessMap = new HashMap<>();

    public boolean isInProcess(Class<?> aClass) {
        return this.inProcessMap.containsKey(aClass);
    }

    public <T> void addProcess(Class<T> aClass, BeanCreation fn) {
        if (!this.isInProcess(aClass)) {
            this.inProcessMap.put(aClass, new ArrayList<>());
        }
        this.inProcessMap.get(aClass).add(fn);
    }

    public void removeProcess(Class<?> aClass) throws NoSuchMethodException {
        if (!this.isInProcess(aClass)) {
            return;
        }
        List<BeanCreation> functions = this.inProcessMap.get(aClass);
        this.inProcessMap.remove(aClass);
        for (BeanCreation fn : functions) {
            fn.create();
        }
    }
}
