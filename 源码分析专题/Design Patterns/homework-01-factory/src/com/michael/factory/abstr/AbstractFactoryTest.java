package com.michael.factory.abstr;

/**
 * Michael created at 2018/3/10
 */
public class AbstractFactoryTest {
    public static void main(String[] args) {
        BikeFactory factory = new BikeFactory();
        System.out.println(factory.getMobike());
        System.out.println(factory.getOfo());
    }
}
