package com.michael.delegate;

/**
 * Michael created at 2018/3/19 22:28
 */
public class WorkerB implements IWork {
    @Override
    public void doWork() {
        System.out.println("工人B开始搬砖");
    }
}
