package com.java8.tutorial.concurrent;

import java.util.concurrent.CompletableFuture;

public class CompletableFuture1 {


    public static void main(String[] args){
        CompletableFuture<String> future = new CompletableFuture<>();

        future.complete("444");

        future.thenAccept(System.out::println)
                .thenAccept(aVoid -> System.out.println("done"));
    }
}
