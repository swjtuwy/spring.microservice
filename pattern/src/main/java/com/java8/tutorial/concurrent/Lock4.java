package com.java8.tutorial.concurrent;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by wangyu on 07/01/2018
 */
public class Lock4 {

    public static void main(String... args){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Map<String, String> map = new HashMap<>();

        StampedLock lock = new StampedLock();

        executorService.submit(()->{
            long stamp = lock.writeLock();
            try {
                ConcurrentUtils.sleep(1);
                map.put("lock4","tes");

            }finally {
                lock.unlockWrite(stamp);
            }
        });


        Runnable readTask = () ->{
            long stamp = lock.readLock();
            try{
                System.out.println(map.get("lock4"));
                ConcurrentUtils.sleep(3);
            }finally {
                lock.unlockRead(stamp);
            }
        };

        executorService.submit(readTask);
        executorService.submit(readTask);

        ConcurrentUtils.stop(executorService);
    }
}
