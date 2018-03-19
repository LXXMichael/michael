package com.michael.delegate;

/**
 * Michael created at 2018/3/19 22:29
 */
public class DelegateTest {
    public static void main(String[] args) {
        // 包工头
        LabourContractor contractor = new LabourContractor();
        contractor.doWork(new WorkerA());
    }
}
