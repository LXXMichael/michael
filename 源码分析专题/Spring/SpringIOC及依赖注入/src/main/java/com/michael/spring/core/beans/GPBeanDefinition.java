package com.michael.spring.core.beans;

/**
 * Michael created at 2018/5/6 9:56
 */
//加载配置文件中的Bean
public class GPBeanDefinition {
    // Bean路径名称
    private String beanClassName;
    // Bean在IOC容器中的名字
    private String factoryBeanName;
    private boolean lazyInit = false;

    public void setBeanClassName(String beanClassName){
        this.beanClassName = beanClassName;
    }

    public String getBeanClassName(){
        return beanClassName;
    }

    public void setFactoryBeanName(String factoryBeanName){
        this.factoryBeanName = factoryBeanName;
    }

    public String getFactoryBeanName(){
        return factoryBeanName;
    }

    public void setLazyInit(boolean lazyInit){
        this.lazyInit = lazyInit;
    }

    public boolean isLazyInit(){
        return lazyInit;
    }
}
