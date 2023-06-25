package ru.nspk.bpp;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Component
public class TimedAnnotationBeanPostProcessor implements BeanPostProcessor {
    private final Map<String, Class<?>> beansWithAnnotatedMethods = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) {
        Class<?> beanClass = bean.getClass();

        for (Method method : beanClass.getMethods()) {
            if (method.isAnnotationPresent(Timed.class)) {
                beansWithAnnotatedMethods.put(beanName, beanClass);
                break;
            }
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {
        var beanClass = beansWithAnnotatedMethods.get(beanName);

        if (beanClass != null) {
            return Proxy.newProxyInstance(
                    beanClass.getClassLoader(),
                    beanClass.getInterfaces(),
                    (proxy, method, args) -> {
                        var origMethod =
                                beanClass.getMethod(method.getName(), method.getParameterTypes());
                        if (origMethod.isAnnotationPresent(Timed.class)) {
                            long start = System.currentTimeMillis();
                            var result = ReflectionUtils.invokeMethod(method, bean, args);
                            long end = System.currentTimeMillis();
                            log.info("Method {} was executed {} ms", method.getName(), end - start);
                            return result;
                        } else {
                            return ReflectionUtils.invokeMethod(method, bean, args);
                        }
                    });
        }

        return bean;
    }
}
