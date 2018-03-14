package jvm.test.chapter05;

public class LongFinalize {

    public static class LF {
        private byte[] content = new byte[512];

        protected void finalize() {
            try {
                System.out.println(Thread.currentThread().getId());
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String... args) {
        long b = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            LF f = new LF();
        }
        long e = System.currentTimeMillis();
        System.out.println(e - b);
    }
}
