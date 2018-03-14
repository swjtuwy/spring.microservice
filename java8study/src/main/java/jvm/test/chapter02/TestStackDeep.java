package jvm.test.chapter02;

public class TestStackDeep {

    private static int count=0;

    public static void recursion(){
        count++;
        recursion();
    }

    public static void main(String... args){
        try {
            recursion();
        }catch (Throwable e){
            System.out.println("deep of call = "+count);
            e.printStackTrace();
        }
    }
}
