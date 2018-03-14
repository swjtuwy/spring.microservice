package jvm.test.chapter05;

import java.util.HashMap;
import java.util.Map;

public class AllocEden {

    public static final int _1k = 1024;

    public static void main(String... args) {
        Map<Integer,byte[]> map = new HashMap<>();

        for (int i = 0; i < 5 * _1k; i++) {
            byte[] b = new byte[_1k];
            map.put(i,b);
        }
    }
}
