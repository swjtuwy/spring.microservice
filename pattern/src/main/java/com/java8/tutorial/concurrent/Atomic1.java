package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

public class Atomic1 {

    private static final int NUM_INCREMENTS = 1000;
    private static AtomicInteger atomicInteger = new AtomicInteger(0);

    public static void main(String... args){

        testUpdate();
        testAccumulate();
        testIncrement();
    }

    private static void testUpdate(){
        atomicInteger.set(0);
        ExecutorService executor = Executors.newFixedThreadPool(2);


        IntStream.range(0,NUM_INCREMENTS).forEach(i->{
            Runnable task = ()->atomicInteger.updateAndGet(n->n+2);
            executor.submit(task);
        });
        ConcurrentUtils.stop(executor);

        System.out.format("update: %d\n", atomicInteger.get());
    }

    private static void testAccumulate(){
        atomicInteger.set(0);
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        IntStream.range(0,NUM_INCREMENTS).forEach(i->{
            Runnable task=()->atomicInteger.accumulateAndGet(i,(n,m)->n+m);
            executorService.submit(task);
        });

        ConcurrentUtils.stop(executorService);

        System.out.format("Accumulate: %d\n",atomicInteger.get());
    }

    private static void testIncrement(){
        atomicInteger.set(0);

        ExecutorService executorService = Executors.newFixedThreadPool(2);
        IntStream.range(0,NUM_INCREMENTS).forEach(i->executorService.submit(atomicInteger::incrementAndGet));

        ConcurrentUtils.stop(executorService);

        System.out.format("increment: expected=%d; is=%d\n",NUM_INCREMENTS, atomicInteger.get());
    }
}
