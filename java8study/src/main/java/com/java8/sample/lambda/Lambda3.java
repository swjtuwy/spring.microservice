package com.java8.sample.lambda;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Predicate;

public class Lambda3 {
    @FunctionalInterface
    interface fun {
        void foo();
    }

    public static void main(String[] args) {
        //predicates

        Predicate<String> predicate = (s -> s.length() > 0);
        predicate.test("foo");
        predicate.negate();

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        //
        Function<String,Integer> toInteger = Integer::valueOf;
    }
}
