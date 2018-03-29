package com.michael.factory.abstr;

import com.michael.factory.entity.Bike;
import com.michael.factory.entity.Mobike;
import com.michael.factory.entity.Ofo;

/**
 * Michael created at 2018/3/10
 * 抽象工厂模式
 */
public class BikeFactory extends AbstractFactory {
    @Override
    Bike getMobike() {
        return new Mobike();
    }

    @Override
    Bike getOfo() {
        return new Ofo();
    }
}
