package com.michael.singleton;

/**
 * Michael created at 2018/3/11 22:26
 * 太阳  双重锁实现太阳实例
 */
public class Sun {
    private Sun(){}

    private static Sun sun = null;

    public static final Sun getInstance(){
        if(null == sun){
            synchronized (Sun.class){
                if(null == sun){
                    sun = new Sun();
                }
            }
        }
        return sun;
    }
}
