package com.java8.tutorial.concurrent;

import java.sql.Array;
import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

public class Executors3 {


    private static void test5() throws Exception{
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                callable("task1",2),
                callable("task3",1),
                callable("task2",2)
        );

        String result = executorService.invokeAny(callables);
        System.out.println(result);

        executorService.shutdown();
    }

    private static Callable<String> callable(String result, long sleepSeconds){
        return ()->{
            TimeUnit.SECONDS.sleep(sleepSeconds);
            return result;
        };
    }

    private static void test4() throws InterruptedException{
        ExecutorService executorService = Executors.newWorkStealingPool();

        List<Callable<String>> callables = Arrays.asList(
                ()->"task1",
                ()->"task2",
                ()->"task3"
        );

        executorService.invokeAll(callables).stream()
                .map(
                        future->{
                            try {
                                return future.get();
                            }catch (Exception ex){
                                throw  new IllegalStateException(ex);
                            }
                        }
                ).forEach(System.out::println);

        executorService.shutdown();
    }

    private static void test3(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () ->{
            try{
                TimeUnit.SECONDS.sleep(3);
                System.out.println("Scheduling: "+System.nanoTime());
            }catch (InterruptedException ex){
                System.err.println("task interrupted");
            }
        };

        executorService.scheduleWithFixedDelay(task,0,1,TimeUnit.SECONDS);
    }

    private static void test2(){
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);

        Runnable task = () -> System.out.println("Scheduling: "+ System.nanoTime());;

        int initialDelay = 0;
        int period = 1;
        executorService.scheduleWithFixedDelay(task,initialDelay,period, TimeUnit.SECONDS);
    }

    private static void test1() throws  Exception{

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);

        Runnable task = () ->System.out.println("scheduling: "+ System.nanoTime());

        int delay = 3;

        ScheduledFuture<?> future = executorService.schedule(task,delay,TimeUnit.SECONDS);

        TimeUnit.MILLISECONDS.sleep(1111);

        long remainingDelay = future.getDelay(TimeUnit.MILLISECONDS);
        System.out.printf("remaining delay: %s ms \n", remainingDelay);

    }
}

