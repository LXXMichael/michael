package com.michael.strategy.ways;

import com.michael.strategy.IWay;

/**
 * Michael created at 2018/3/19 21:48
 */
public class ByCarWay implements IWay {
    @Override
    public void go() {
        System.out.println("开着大奔去旅行");
    }
}
