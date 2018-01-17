package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by wangyu on 07/01/2018
 */
public class Lock5 {

    public static void main(String... args){

        ExecutorService executorService = Executors.newFixedThreadPool(3);

        StampedLock lock = new StampedLock();

        executorService.submit(()->{
            long stamp = lock.tryOptimisticRead();
            try {
                System.out.println("Optimistic Lock Valid: "+ lock.validate(stamp));
                ConcurrentUtils.sleep(1);
                System.out.println("Optimistic Lock Valid: "+ lock.validate(stamp));
                ConcurrentUtils.sleep(2);
                System.out.println("Optimistic Lock Valid: "+ lock.validate(stamp));
            }finally {
                lock.unlock(stamp);
            }
        });


        executorService.submit(()->{
            long stamp =  lock.writeLock();
            try {
                System.out.println("Write lock acquired");
                ConcurrentUtils.sleep(3);
            }finally {
                lock.unlock(stamp);
                System.out.println("write done");
            }
        });

        ConcurrentUtils.stop(executorService);
    }
}
