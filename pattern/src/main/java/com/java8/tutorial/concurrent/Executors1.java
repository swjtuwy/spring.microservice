package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Executors1 {

    public static void main(String... args){
        test1(12);
    }
    private static void test1(long seconds) {
        ExecutorService executorService = Executors.newSingleThreadExecutor();

        executorService.submit(() -> {
                    try {
                        TimeUnit.SECONDS.sleep(seconds);
                        String name = Thread.currentThread().getName();
                        System.out.println("task finished: "+name);
                    }catch (InterruptedException ex){
                        System.err.println("task interrupted");
                        ex.printStackTrace();
                    }
                }
        );

    }
    static void stop(ExecutorService executorService){
        try {
            System.out.println("attempt to shutdown executor");
            executorService.shutdown();
            executorService.awaitTermination(5,TimeUnit.SECONDS);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
        finally {
            if(!executorService.isTerminated()){
                System.out.println("killing non-finished task");
            }
            executorService.shutdownNow();
            System.out.println("shutdown finished");
        }
    }
}
