package com.michael.spring.core.beans;

/**
 * Michael created at 2018/5/6 12:16
 */
// 用于做事件监听
public class GPBeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName){
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName){
        return bean;
    }
}
