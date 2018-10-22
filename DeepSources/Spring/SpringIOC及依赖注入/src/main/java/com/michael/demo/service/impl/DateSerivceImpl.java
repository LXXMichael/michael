package com.michael.demo.service.impl;

import com.michael.demo.service.IDateService;

import java.util.Calendar;
import java.util.Date;

/**
 * Michael created at 2018/5/6 15:04
 */
public class DateSerivceImpl implements IDateService {
    @Override
    public Date now() {
        return Calendar.getInstance().getTime();
    }
}
