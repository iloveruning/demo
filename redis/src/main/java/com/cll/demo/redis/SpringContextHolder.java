package com.cll.demo.redis;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@Component
public class SpringContextHolder implements ApplicationContextAware {

    private static ApplicationContext applicationContext;


    public static <T> T getBean(Class<T> type) {
        return getContext().getBean(type);
    }

    public static Object getBean(String beanName) {
        return getContext().getBean(beanName);
    }


    public static ApplicationContext getContext() {
        if (applicationContext == null) {
            throw new NullPointerException("applicationContext is not aware by spring container");
        }
        return applicationContext;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextHolder.applicationContext = applicationContext;
    }
}
