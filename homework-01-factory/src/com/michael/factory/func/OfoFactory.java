package com.michael.factory.func;

import com.michael.factory.entity.Bike;
import com.michael.factory.entity.Ofo;

/**
 * Michael created at 2018/3/10
 * OFO单车
 */
public class OfoFactory implements BikeFactory {
    @Override
    public Bike getBike() {
        return new Ofo();
    }
}
