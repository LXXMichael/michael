package com.michael.factory.func;

/**
 * Michael created at 2018/3/10
 * 工厂方法模式
 */
public class FuncFactoryTest {
    public static void main(String[] args) {
        BikeFactory factory = new MobikeFactory();
        System.out.println(factory.getBike());

        factory = new OfoFactory();
        System.out.println(factory.getBike());
    }
}
