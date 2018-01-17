package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * Created by wangyu on 07/01/2018
 */
public class Semaphore1 {

    private static final int NUM_INCREMENTS = 10000;

    private static Semaphore semaphore = new Semaphore(3);

    private static int count=0;

    public static void main(String... args){
        testIncrement();
    }

    private static void testIncrement(){
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0, NUM_INCREMENTS)
                .forEach(i->executorService.submit(Semaphore1::increment));

        ConcurrentUtils.stop(executorService);

        System.out.println("increment : "+count);
    }

    private static void increment(){
        boolean permit = false;

        try {
            permit = semaphore.tryAcquire(5, TimeUnit.SECONDS);
            count++;
        }catch (InterruptedException ex){
            throw new RuntimeException(ex);
        }finally {
            if(permit){
                semaphore.release();
            }
        }
    }
}
