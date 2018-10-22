package com.michael.spring.core.beans;

/**
 * Michael created at 2018/5/6 9:56
 */
// 对Bean进行包装，增强Bean的能力  装饰器模式
public class GPBeanWrapper extends GPFactoryBean {
    // 原生对象
    private Object originalInstance;
    // 包装类对象
    private Object wrapperInstance;
    // 事件监听
    private GPBeanPostProcessor postProcessor;

    public GPBeanWrapper(Object instance){
        this.originalInstance = instance;
        this.wrapperInstance = instance;
    }

    public Object getWrappedInstance(){
        return wrapperInstance;
    }

    public Class<?> getWrappedClass(){
        return wrapperInstance.getClass();
    }

    public GPBeanPostProcessor getPostProcessor() {
        return postProcessor;
    }

    public void setPostProcessor(GPBeanPostProcessor postProcessor) {
        this.postProcessor = postProcessor;
    }
}
