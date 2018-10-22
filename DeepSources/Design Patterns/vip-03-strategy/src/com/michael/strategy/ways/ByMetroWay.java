package com.michael.strategy.ways;

import com.michael.strategy.IWay;
import com.michael.strategy.WayType;

/**
 * Michael created at 2018/3/19 21:37
 */
public class ByMetroWay implements IWay {
    @Override
    public void go() {
        System.out.println("乘坐地铁去旅行");
    }
}
