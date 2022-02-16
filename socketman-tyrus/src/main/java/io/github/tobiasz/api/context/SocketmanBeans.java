package io.github.tobiasz.api.context;

import io.github.tobiasz.api.annotation.Component;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.ReflectionUtils;
import org.reflections.Reflections;

public class SocketmanBeans {

    private final BeanFactory beanFactory = new BeanFactory();

    public void initBeans(Reflections reflections) {
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Component.class);
        System.out.println(typesAnnotatedWith);
        for (Class<?> aClass : typesAnnotatedWith) {
            if (this.beanFactory.hasBean(aClass)) {
                continue;
            }
            try {
                this.beanFactory.createBean(aClass);
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

}
