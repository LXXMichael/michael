package com.michael.template;

/**
 * Michael created at 2018/3/19 22:05
 */
public class MilkTea extends MakeTea {
    @Override
    void putTea() {
        System.out.println("倒入果汁");
    }

    @Override
    void putCondiments() {
        System.out.println("加糖");
    }
}
