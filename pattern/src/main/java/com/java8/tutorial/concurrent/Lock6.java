package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by wangyu on 07/01/2018
 */
public class Lock6 {

    public static int count=0;

    public static void main(String... args){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        StampedLock lock = new StampedLock();

        executorService.submit(()->{
            long stamp = lock.readLock();

            try {
                if(count==0){
                    stamp = lock.tryConvertToWriteLock(stamp);
                    if(stamp==0L){
                        System.out.println("could not convert to write lock");
                        stamp = lock.writeLock();
                    }
                    count =10;
                }

                System.out.println(count);
            }finally {
                lock.unlock(stamp);
            }
        });

        ConcurrentUtils.stop(executorService);
    }
}
