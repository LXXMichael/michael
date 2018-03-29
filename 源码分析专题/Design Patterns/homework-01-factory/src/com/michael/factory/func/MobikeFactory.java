package com.michael.factory.func;

import com.michael.factory.entity.Bike;
import com.michael.factory.entity.Mobike;

/**
 * Michael created at 2018/3/10
 * 摩拜单车
 */
public class MobikeFactory implements BikeFactory {
    @Override
    public Bike getBike() {
        return new Mobike();
    }
}
