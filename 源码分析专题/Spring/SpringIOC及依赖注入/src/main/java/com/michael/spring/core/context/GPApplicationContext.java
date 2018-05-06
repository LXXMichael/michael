package com.michael.spring.core.context;

import com.michael.demo.controller.DemoController;
import com.michael.spring.annotation.GPAutowired;
import com.michael.spring.annotation.GPController;
import com.michael.spring.annotation.GPService;
import com.michael.spring.core.GPBeanFactory;
import com.michael.spring.core.beans.GPBeanDefinition;
import com.michael.spring.core.beans.GPBeanPostProcessor;
import com.michael.spring.core.beans.GPBeanWrapper;
import com.michael.spring.core.support.GPBeanDefinitionReader;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Michael created at 2018/5/6 9:27
 */
public class GPApplicationContext implements GPBeanFactory {

    private String[] contextLocations;

    private GPBeanDefinitionReader reader;

    // 用来保存配置信息
    private Map<String,GPBeanDefinition> beanDefinitionMap = new ConcurrentHashMap<String,GPBeanDefinition>();

    // 保证注册式单例
    private Map<String,Object> beanCacheMap = new HashMap<String,Object>();

    // 存储所有的代理类实例
    private Map<String,GPBeanWrapper> beanWrapperMap = new ConcurrentHashMap<>();

    public GPApplicationContext(String ... contextLocations){
        this.contextLocations = contextLocations;
        refresh();
    }

    private void refresh(){
        // 定位
        reader = new GPBeanDefinitionReader(contextLocations);
        // 加载
        List<String> beanDefinitions = reader.loadBeanDefinitions();
        // 注册
        doRegistry(beanDefinitions);
        System.out.println("BeanDefinitionsMap:"+beanDefinitionMap);
        // 依赖注入 自动调用getBean方法
        doAutowired();

        DemoController controller = (DemoController)getBean("demoController");
//        controller.sayHello(null,null,"Michael");
        controller.welcomeWithDate(null,null,"Michael");
    }

    private void doAutowired() {
        if(beanDefinitionMap.isEmpty())return;
        for(Map.Entry<String,GPBeanDefinition> entry : beanDefinitionMap.entrySet()){
            // 不是懒加载时调用
            if(!entry.getValue().isLazyInit()){
                getBean(entry.getKey());
            }
        }

        for(Map.Entry<String,GPBeanWrapper> entry : beanWrapperMap.entrySet()){
            populateBean(entry.getKey(),entry.getValue().getWrappedInstance());
        }
    }

    // 将Bean注册到IOC容器BeanDefinitionsMap中
    private void doRegistry(List<String> beanDefinitions) {
        try {
            for (String beanDefinition : beanDefinitions) {
                Class<?> beanClass = Class.forName(beanDefinition);
                // 接口，不能实例化 注册子类时处理接口  其实也不影响结果，子类会覆盖
                if(beanClass.isInterface()) continue;
                GPBeanDefinition gpBeanDefinition = reader.registerBeanDefinition(beanDefinition);
                if(null != gpBeanDefinition){
                    // IOC容器
                    beanDefinitionMap.put(gpBeanDefinition.getFactoryBeanName(),gpBeanDefinition);
                }
                // 接口，用接口全称做key值  这里若有多个实例会覆盖
                Class<?>[] interfaceClasses = beanClass.getInterfaces();
                for(Class<?> interfaceClazz : interfaceClasses){
                    beanDefinitionMap.put(interfaceClazz.getName(),gpBeanDefinition);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    // 真正的依赖注入，会实例化Bean
    // Spring不会返回真正的Bean，而是返回BeanWrapper对象，装饰器模式，为AOP打基础
    @Override
    public Object getBean(String beanName) {
        GPBeanDefinition gpBeanDefinition = beanDefinitionMap.get(beanName);
        // 初始化className
        Object instance = doInitiateBean(gpBeanDefinition);
        if(null == instance) return null;

        // 注册监听 代理类生成前触发事件
        GPBeanPostProcessor postProcessor = new GPBeanPostProcessor();
        postProcessor.postProcessBeforeInitialization(instance,beanName);

        GPBeanWrapper beanWrapper = new GPBeanWrapper(instance);
        beanWrapperMap.put(beanName,beanWrapper);

        // 代理类生成后触发事件
        postProcessor.postProcessAfterInitialization(instance,beanName);

        //不能在这里调用，初始化Controller时Service不一定初始化
//        populateBean(beanName,instance);
        return beanWrapper.getWrappedInstance();
    }

    private Object doInitiateBean(GPBeanDefinition gpBeanDefinition) {
        String beanClassName = gpBeanDefinition.getBeanClassName();
        try {
            if(!beanCacheMap.containsKey(beanClassName)){
                Class<?> beanClazz = Class.forName(beanClassName);
                beanCacheMap.put(beanClassName,beanClazz.newInstance());
            }
            return beanCacheMap.get(beanClassName);
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    // 真正的依赖注入的方法
    private void populateBean(String beanName,Object instance){
        Class<?> clazz = instance.getClass();
        if(!(clazz.isAnnotationPresent(GPController.class) || clazz.isAnnotationPresent(GPService.class))) return;

        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(!field.isAnnotationPresent(GPAutowired.class)) continue;
            beanName = field.getType().getName();
            boolean accessible = field.isAccessible();
            field.setAccessible(true);
            try {
                field.set(instance,beanWrapperMap.get(beanName).getWrappedInstance());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }finally{
                field.setAccessible(accessible);
            }
        }
    }
}
