package com.java8.tutorial.concurrent;

import java.sql.Time;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Executors2 {

    public static void main(String... args){

    }

    private static void test3() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
                return 2222;
            }
            catch (InterruptedException ex){
                throw new IllegalStateException("task interrupted", ex);
            }
        });

        future.get(1,TimeUnit.SECONDS);
    }

    private static void test2() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(() ->{
            try {
                TimeUnit.SECONDS.sleep(1);
                return 1;
            } catch (Exception e){
                throw new IllegalStateException("task interrupted",e);
            }
        });

        executorService.shutdownNow();
        future.get();
    }

    private static void test1() throws Exception{
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        Future<Integer> future = executorService.submit(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
                return 222;
            }catch (Exception ex){
               throw new IllegalStateException("task interrupted",ex);
            }
        });

        System.out.println("future done: "+future.isDone());

        Integer result = future.get();

        System.out.println("future done: "+future.isDone());
        System.out.println("result : "+result);
        executorService.shutdownNow();
    }
}
