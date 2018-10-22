package com.michael.strategy;

/**
 * Michael created at 2018/3/19 21:45
 */
public class StrategyTest {
    public static void main(String[] args) {
        TravelWay way = new TravelWay();
        way.goTravel(WayType.BY_METRO);
    }
}
