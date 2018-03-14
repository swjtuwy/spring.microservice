package jvm.test.chapter02;

public class CanReliverObj {

    public static CanReliverObj obj;

    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("can re live object finalize called");
        obj = this;
    }

    public String toString() {
        return "i am canreliveObj";
    }

    public static void main(String... args) throws InterruptedException {
        obj = new CanReliverObj();
        obj = null;
        System.gc();
        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj is usable");
        }

        System.out.println("gc seconds");
        obj = null;
        System.gc();

        Thread.sleep(1000);
        if (obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj is usable");
        }


    }

}
