package com.current.test;

public class VolatileDemo {


    volatile int a = 0;

    public void addNum() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        a++;//注意这里
    }

    public static void main(String[] args) {
        final VolatileDemo volatileDemo = new VolatileDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    volatileDemo.addNum();
                    System.out.println("num=" + volatileDemo.a);
                }
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 5; i++) {
                    volatileDemo.addNum();
                    System.out.println("num=" + volatileDemo.a);
                }
            }
        }).start();
    }
}
