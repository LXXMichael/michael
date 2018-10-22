package com.michael.adapter;

/**
 * Michael created at 2018/3/19 22:33
 * Type C接口充电线，需要转化为标准线才能使用
 */
public class TypeCLine{
    private StandardLine standardLine;

    public TypeCLine(StandardLine standardLine){
        this.standardLine = standardLine;
    }
    public void charge() {
        System.out.println("我是Type C接口，需要转化为标准接口才能充电");
        System.out.println("从TypeC接口转化为标准线接口");
        standardLine.charge();
    }
}
