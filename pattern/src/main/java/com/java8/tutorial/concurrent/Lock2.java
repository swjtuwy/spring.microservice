package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by wangyu on 06/01/2018
 */
public class Lock2 {

    public static void main(String[] args){
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        ReentrantLock lock = new ReentrantLock();

        executorService.submit(()->{
            lock.lock();
            try{
                ConcurrentUtils.sleep(1);
            }finally {
                lock.unlock();
            }
        });

        executorService.submit(()->{
            System.out.println("locked: "+lock.isLocked());
            System.out.println("held by me: "+lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();

            System.out.println("lock acquired: "+ locked);
        });

        executorService.submit(()->{
            System.out.println("Locked: "+lock.isLocked());
            System.out.println("held by me: "+lock.isHeldByCurrentThread());
            boolean locked = lock.tryLock();
            System.out.println("Lock acquired:"+locked);
        });

        ConcurrentUtils.stop(executorService);
    }
}
