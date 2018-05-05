package com.michael.core.servlet;

import com.michael.core.annotation.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;

/**
 * Michael created at 2018/5/5 16:30
 */
public class GPServletDispatcher extends HttpServlet{

    private Properties p = new Properties();

    private List<String> classNames = new ArrayList<String>();

    private Map<String,Object> ioc = new HashMap<String,Object>();

    private Map<String,Handler> handlerMappings = new HashMap<String,Handler>();

    @Override
    public void init(ServletConfig config) throws ServletException {
        // 定位
        doLoadConfig(config.getInitParameter("contextLocations"));
        String basePackage = p.getProperty("basePackage");
        System.out.println("Gupaoedu config init success,basePackage="+basePackage);
        // 加载
        doScan(basePackage);
        System.out.println(classNames);
        // 注册
        doRegistry();
        System.out.println("Gupaoedu ioc initialize,ioc:" + ioc);
        // 注入
        doAutowired();

        // url与Method间的调用关系
        initHandlerMappings();
        System.out.println("Gupaoedu handlerMappings init success,handlerMappings:" + handlerMappings);
    }

    private void initHandlerMappings() {
        if(ioc.isEmpty())return;
        for(Map.Entry<String,Object> entry : ioc.entrySet()){
            Class<?> clazz = entry.getValue().getClass();
            String url = "";
            if(clazz.isAnnotationPresent(GPController.class)){
                if(clazz.isAnnotationPresent(GPRequestMapping.class)){
                    String mapping = clazz.getAnnotation(GPRequestMapping.class).value();
                    if(!"".equals(mapping)){
                        url = mapping;
                    }
                }

                Method[] methods = clazz.getDeclaredMethods();
                for(Method m : methods){
                    if(!m.isAnnotationPresent(GPRequestMapping.class))continue;
                    String mapping = m.getAnnotation(GPRequestMapping.class).value();
                    mapping = (url + "/" + mapping).replaceAll("//+","/");
                    handlerMappings.put(mapping,new Handler(entry.getValue(),m));

                }
            }
        }
    }

    private void doRegistry() {
        if(classNames.isEmpty()) return;
        try {
            for (String className : classNames) {
                Class<?> clazz = Class.forName(className);
                if(clazz.isAnnotationPresent(GPController.class)){
                    ioc.put(lowerFirstCase(clazz.getSimpleName()),clazz.newInstance());
                }else if(clazz.isAnnotationPresent(GPService.class)){
                    // service
                    String mapping = clazz.getAnnotation(GPService.class).value();
                    Object instance = clazz.newInstance();
                    if("".equals(mapping)){
                        ioc.put(lowerFirstCase(clazz.getSimpleName()),instance);
                    }
                    // service name
                    else{
                        ioc.put(mapping,instance);
                    }
                    // interface
                    Class<?>[] interfaces = clazz.getInterfaces();
                    for(Class<?> in : interfaces){
                        ioc.put(in.getName(),instance);
                    }
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private String lowerFirstCase(String str){
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    private void doAutowired() {
        if(ioc.isEmpty()) return;
        for(Map.Entry<String,Object> entry : ioc.entrySet()){
            Class<?> clazz = entry.getValue().getClass();
            if(!clazz.isAnnotationPresent(GPController.class)) continue;
            Field[] fields = clazz.getDeclaredFields();
            for(Field f : fields){
                if(!f.isAnnotationPresent(GPAutowired.class)) continue;
                boolean accessible = f.isAccessible();
                f.setAccessible(true);
                try {
                    f.set(entry.getValue(),ioc.get(f.getType().getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }finally{
                    f.setAccessible(accessible);
                }
            }
        }
    }

    private void doScan(String filePath) {
        URL url = this.getClass().getClassLoader().getResource("/" + filePath.replaceAll("\\.","/"));
        File dir = new File(url.getFile());
        if(!dir.exists()){
            System.out.println("文件目录"+dir.getName() +"不存在");
            return;
        }
        File[] files = dir.listFiles();
        for (File f : files){
            if(f.isDirectory()){
                doScan(filePath + "." + f.getName());
            }
            if(f.getName().endsWith(".class")){
                classNames.add(filePath + "." + f.getName().replace(".class",""));
            }
        }
    }

    private void doLoadConfig(String contextLocations) {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(contextLocations.replace("classpath:","/"));
        try {
            p.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            if(null != in){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI();
        String context = req.getContextPath();

        url = url.replace(context,"");
        if(null == url || "".equals(url.trim())){
            resp.getWriter().write("404,Request Error!");
            return;
        }
        System.out.println("Gupaoedu request url:"+url);
        Handler handler = handlerMappings.get(url);
        if(null == handler){
            resp.getWriter().write("404,Request Error!");
            return;
        }
        // 参数Map
//        Map<String,Integer> paramMap = handler.getParamsMap();
        try {
            handler.getMethod().invoke(handler.getInstance(),req,resp,req.getParameter("name"));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


}
