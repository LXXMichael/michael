package com.michael.template;

/**
 * Michael created at 2018/3/19 21:51
 */
public abstract class MakeTea {

    protected void make(){
        //1、烧水
        System.out.println("把水烧开");
        // 2、温具
        System.out.println("用热水冲淋茶具");

        // 3、放茶叶
        putTea();
        // 4、加入其它调料
        putCondiments();
        // 5、加入开水
        System.out.println("倒入开水");
    }

    abstract void putTea();

    abstract void putCondiments();
}
