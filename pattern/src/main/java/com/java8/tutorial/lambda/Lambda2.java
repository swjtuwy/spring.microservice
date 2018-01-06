package com.java8.tutorial.lambda;

public class Lambda2 {

    @FunctionalInterface
    public static interface Converter<F, T> {
        T convert(F from);
    }

    static class Something {
        String startsWith(String s) {
            return String.valueOf(s.charAt(0));
        }
    }

    interface PersonFactory<P extends Person> {
        P create(String firstName, String lastName);
    }

    public static void main(String[] args) {
        Converter<String, Integer> integerConverter1 = (from -> Integer.valueOf(from));
        Integer convert1 = integerConverter1.convert("123");
        System.out.println(convert1);

        Converter<String, Integer> integerConverter2 = Integer::valueOf;
        Integer converted2 = integerConverter2.convert("123");
        System.out.println(converted2);

        Something something = new Something();
        Converter<String, String> stringConverter = something::startsWith;
        String converted3 = stringConverter.convert("java");
        System.out.println(converted3);

        PersonFactory<Person> personFactory = Person::new;
        Person person = personFactory.create("perter", "parker");


    }
}
