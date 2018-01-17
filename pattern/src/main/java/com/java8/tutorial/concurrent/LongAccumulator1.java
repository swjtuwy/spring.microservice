package com.java8.tutorial.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAccumulator;
import java.util.function.LongBinaryOperator;
import java.util.stream.IntStream;

/**
 * Created by wangyu on 07/01/2018
 */
public class LongAccumulator1 {

    public static void main(String[] args){
        testAccumulate();
    }

    private static void testAccumulate(){
        LongBinaryOperator op = (x,y)->2*x+y;
        LongAccumulator accumulator = new LongAccumulator(op, 1L);
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        IntStream.range(0, 10).forEach(i->executorService.submit(
                ()->accumulator.accumulate(i)
        ));

        ConcurrentUtils.stop(executorService);

        System.out.format("add %s \n", accumulator.getThenReset());
    }
}
