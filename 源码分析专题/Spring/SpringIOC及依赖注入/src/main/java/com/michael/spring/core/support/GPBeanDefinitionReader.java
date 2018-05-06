package com.michael.spring.core.support;

import com.michael.spring.core.beans.GPBeanDefinition;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * Michael created at 2018/5/6 9:46
 */
// 定位、加载、读取配置文件中的信息，返回Bean的信息
public class GPBeanDefinitionReader {
    private static final String BASE_PACKAGE = "basePackage";

    //配置文件信息，以properties文件代替
    private Properties config = new Properties();

    // 所有的Bean信息
    private List<String> beanClassNames = new ArrayList<String>();

    public GPBeanDefinitionReader(String ... contextLocations){
        // 定位配置文件信息
        doLoadConfig(contextLocations);
        // 加载Bean信息
        doScanner(config.getProperty(BASE_PACKAGE));
    }

    private void doScanner(String filePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + filePath.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        if(null == dir){
            System.out.println("文件目录"+dir+"不存在");
            return;
        }
        for(File file : dir.listFiles()){
            if(file.isDirectory()){
                doScanner(filePath + "." + file.getName());
            }
            if(file.getName().endsWith(".class")){
                beanClassNames.add(filePath + "." + file.getName().replace(".class",""));
            }
        }
    }

    private void doLoadConfig(String[] contextLocations) {
        for(String contextLocation : contextLocations){
            InputStream ins = this.getClass().getClassLoader().getResourceAsStream(contextLocation);
            try {
                config.load(ins);
            } catch (IOException e) {
                e.printStackTrace();
            }finally{
                try {
                    if(null != ins){
                        ins.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public List<String> loadBeanDefinitions(){
        return beanClassNames;
    }

    public GPBeanDefinition registerBeanDefinition(String beanName){
        if(beanClassNames.contains(beanName)){
            GPBeanDefinition gpBeanDefinition = new GPBeanDefinition();
            gpBeanDefinition.setBeanClassName(beanName);
            // 该Bean在IOC容器中的名称
            gpBeanDefinition.setFactoryBeanName(lowerFirstCase(beanName.substring(beanName.lastIndexOf(".") + 1)));
            return gpBeanDefinition;
        }
        return null;
    }

    private String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }
}
