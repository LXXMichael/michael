package com.michael.strategy;

/**
 * Michael created at 2018/3/19 21:26
 */
public class TravelWay {

    public void goTravel(WayType type) {
        System.out.println("选择出行方式：" + type.getDescription());
        type.getWay().go();
    }
}
