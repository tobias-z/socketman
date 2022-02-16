package io.github.tobiasz.api.context;

import java.util.HashMap;
import java.util.Map;

public class BeanFactory {

    private final Map<Class<?>, ?> beanMap = new HashMap<>();
    private final InProcessContext inProcessContext = new InProcessContext();

    public boolean hasBean(Class<?> bean) {
        return this.beanMap.containsKey(bean);
    }

    public <T> T getBean(Class<T> bean) {
        if (!this.hasBean(bean)) {
            return null;
        }
        return (T) this.beanMap.get(bean);
    }

    public void createBean(Class<?> bean) throws NoSuchMethodException {
        BeanCreation beanCreation = this.getBeanCreationFunction(bean);
        if (this.inProcessContext.isInProcess(bean)) {
            this.inProcessContext.addProcess(bean, beanCreation);
        } else {
            beanCreation.create();
            this.inProcessContext.removeProcess(bean);
        }
    }

    private BeanCreation getBeanCreationFunction(Class<?> bean) {
        return () -> {
            for (Class<?> aClass : bean.getConstructor().getParameterTypes()) {
                System.out.println(aClass);
            }
        };
    }
}
