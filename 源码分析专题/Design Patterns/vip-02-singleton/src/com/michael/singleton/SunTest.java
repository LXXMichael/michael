package com.michael.singleton;

import java.util.concurrent.CountDownLatch;

/**
 * Michael created at 2018/3/11 22:33
 */
public class SunTest {
    public static void main(String[] args) {
        int count = 100;
        final CountDownLatch latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for(int i=0;i<count;i++){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Sun s = Sun.getInstance();
                    System.out.println(s);
                    latch.countDown();
                }
            }).start();
        }

        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end = System.currentTimeMillis();
        System.out.println("执行成功，花费了"+(end-start)+"毫秒");
    }
}
