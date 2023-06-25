package ru.nspk.bfpp;

import java.util.Arrays;
import java.util.Iterator;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class WarmUpBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) {
        for (Iterator<String> it = beanFactory.getBeanNamesIterator(); it.hasNext(); ) {
            String beanName = it.next();
            try {
                BeanDefinition beanDefinition = beanFactory.getBeanDefinition(beanName);
                String beanClassName = beanDefinition.getBeanClassName();

                if (beanClassName != null) {
                    Class<?> beanClass = Class.forName(beanClassName);
                    boolean classImplemented =
                            Arrays.asList(beanClass.getInterfaces()).contains(IWarmUp.class);
                    if (classImplemented) {
                        beanDefinition.setInitMethodName("warmUp");
                    }
                }
            } catch (NoSuchBeanDefinitionException ignored) {
                // doing nothing
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
