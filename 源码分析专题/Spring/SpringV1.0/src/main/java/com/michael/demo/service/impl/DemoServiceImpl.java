package com.michael.demo.service.impl;

import com.michael.core.annotation.GPService;
import com.michael.demo.service.IDemoService;

/**
 * Michael created at 2018/5/5 18:53
 */
@GPService
public class DemoServiceImpl implements IDemoService {
    public String hello(String name) {
        return "Hello " + name;
    }

    public String welcome(String name) {
        return "Welcome to Gupaoedu," + name;
    }
}
