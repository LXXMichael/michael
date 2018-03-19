package com.michael.delegate;

/**
 * Michael created at 2018/3/19 22:27
 */
public class WorkerA implements IWork {
    @Override
    public void doWork() {
        System.out.println("工人A开始搬砖");
    }
}
