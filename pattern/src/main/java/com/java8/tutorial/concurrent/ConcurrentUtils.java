package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public class ConcurrentUtils {

    public static void stop(ExecutorService executorService){
        try{
            executorService.shutdown();
            executorService.awaitTermination(60, TimeUnit.SECONDS);

        }catch (InterruptedException ex){
//            System.err.println(ex);
            ex.printStackTrace();
        }
        finally {
            if(!executorService.isTerminated()){
                System.err.println("killing non finished task");
            }
            executorService.shutdownNow();
        }
    }

    public static void sleep(int seconds){
        try {
            TimeUnit.SECONDS.sleep(seconds);
        }catch (InterruptedException ex){
            ex.printStackTrace();
        }
    }
}
