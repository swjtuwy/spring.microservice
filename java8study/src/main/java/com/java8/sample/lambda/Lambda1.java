package com.java8.sample.lambda;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Lambda1 {

    public static void main(String[] args) {
        List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");

        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        Collections.sort(names, (String a, String b) -> {
            return b.compareTo(a);
        });

        Collections.sort(names, (String a, String b) -> b.compareTo(a));

        Collections.sort(names, (a,b) ->b.compareTo(a));

        System.out.println(names);

        List<String> names2= Arrays.asList("peter", null, "anna", "mike", "xenia");

//        names2.sort(Comparator.nullsLast(String::compareTo));
        names2.sort(Comparator.nullsFirst(String::compareTo));
        System.out.println(names2);


        List<String> names3 = null;

        Optional.ofNullable(names3).ifPresent(list-> list.sort(Comparator.naturalOrder()));
//        names3.sort(Comparator.naturalOrder());
        System.out.println(names3);
    }
}
