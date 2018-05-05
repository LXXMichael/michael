package com.michael.demo.controller;

import com.michael.core.annotation.GPAutowired;
import com.michael.core.annotation.GPController;
import com.michael.core.annotation.GPRequestMapping;
import com.michael.core.annotation.GPRequestParam;
import com.michael.demo.service.IDemoService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Michael created at 2018/5/5 18:52
 */
@GPController
@GPRequestMapping
public class DemoController {

    @GPAutowired
    private IDemoService service;

    @GPRequestMapping("/sayHello.do")
    public void sayHello(HttpServletRequest request, HttpServletResponse response,@GPRequestParam("name") String name){
        String result = service.hello(name);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GPRequestMapping("/welcome.do")
    public void welcome(HttpServletRequest request, HttpServletResponse response,@GPRequestParam("name") String name){
        String result = service.welcome(name);
        try {
            response.getWriter().write(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
