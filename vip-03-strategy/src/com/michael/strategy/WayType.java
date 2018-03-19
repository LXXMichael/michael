package com.michael.strategy;

import com.michael.strategy.ways.*;

/**
 * Michael created at 2018/3/19 21:23
 */
public enum WayType {
    BY_BUS(new ByBusWay(),"乘坐公共汽车"),
    BY_TRAIN(new ByTrainWay(),"乘坐火车"),
    BY_METRO(new ByMetroWay(),"乘坐地铁"),
    BY_BICYCLE(new ByBicycleWay(),"骑自行车"),
    BY_PLAIN(new ByPlainWay(),"乘坐飞机"),
    BY_CAR(new ByCarWay(),"开车"),
    BY_FOOT(new ByFootWay(),"走路");

    private IWay way;
    private String description;

    WayType(IWay way, String description) {
        this.way = way;
        this.description = description;
    }

    public IWay getWay() {
        return way;
    }

    public void setWay(IWay way) {
        this.way = way;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
