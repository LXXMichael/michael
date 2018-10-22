package com.michael.demo.service.impl;

import com.michael.demo.service.IDateService;
import com.michael.demo.service.IDemoService;
import com.michael.spring.annotation.GPAutowired;
import com.michael.spring.annotation.GPService;

/**
 * Michael created at 2018/5/5 18:53
 */
@GPService
public class DemoServiceImpl implements IDemoService {

    @GPAutowired
    private IDateService dateService;

    public String hello(String name) {
        return "Hello " + name;
    }

    public String welcome(String name) {
        return "Welcome to Gupaoedu," + name;
    }

    @Override
    public String welcomeWithDate(String name) {
        return "Welcome to Gupaoedu,"+name+",Now is "+ dateService.now();
    }
}
