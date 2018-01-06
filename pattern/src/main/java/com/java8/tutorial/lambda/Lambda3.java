package com.java8.tutorial.lambda;


import java.util.Comparator;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Supplier;

public class Lambda3 {

    @FunctionalInterface
    interface fun {
        void foo();
    }

    public static void main(String[] args) throws Exception {
        //Predicates

        Predicate<String> predicate = s -> s.length() > 0;

        predicate.test("foo");              //true
        predicate.negate().test("foo");     //false

        Predicate<Boolean> nonNull = Objects::nonNull;
        Predicate<Boolean> isNull = Objects::isNull;

        Predicate<String> isEmpty = String::isEmpty;
        Predicate<String> isNotEmpty = isEmpty.negate();

        // Functions
        Function<String, Integer> toInteger = Integer::valueOf;
        Function<String,String > backToString = toInteger.andThen(String::valueOf);

        backToString.apply("123");

        Supplier<Person> personSupplier = Person::new;
        personSupplier.get();  // new person

        // consumers
        Consumer<Person> greeter = person -> System.out.println("hello, "+person.firstName);
        greeter.accept(new Person("lucke", "Skywalker"));

        // comparators

        Comparator<Person> comparator = (p1, p2)->p1.firstName.compareTo(p2.firstName);
        Person p1 = new Person("john","a");
        Person p2 = new Person("Alice", "b");

        comparator.compare(p1,p2);

        comparator.reversed().compare(p1,p2);

        // Runnables
        Runnable runnable = () -> System.out.println(UUID.randomUUID());
        runnable.run();

        // callables
        Callable<UUID> callable = UUID::randomUUID;
        UUID a = callable.call();
        System.out.print(a);
    }
}
