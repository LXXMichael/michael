package com.michael.core.servlet;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Michael created at 2018/5/5 21:15
 */
public class Handler {
    private Object instance;
    private Method method;

    public Handler(Object instance, Method method) {
        this.instance = instance;
        this.method = method;
    }

    public Object getInstance() {
        return instance;
    }

    public void setInstance(Object instance) {
        this.instance = instance;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "Handler{" +
                "instance=" + instance +
                ", method=" + method +
                '}';
    }
}
