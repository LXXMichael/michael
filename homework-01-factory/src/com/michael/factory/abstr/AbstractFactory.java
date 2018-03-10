package com.michael.factory.abstr;

import com.michael.factory.entity.Bike;

/**
 * Michael created at 2018/3/10
 */
public abstract class AbstractFactory {
    abstract Bike getMobike();

    abstract Bike getOfo();
}
