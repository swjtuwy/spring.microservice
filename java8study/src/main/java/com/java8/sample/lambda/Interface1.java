package com.java8.sample.lambda;

public class Interface1 {

    interface Formula {
        double calculate(int a);

        default double sqrt(int a) {
            return Math.sqrt(positive(a));
        }

        static int positive(int a) {
            return a > 0 ? a : 0;
        }
    }

    public static void main(String[] args){
        Formula formula1 = new Formula() {
            @Override
            public double calculate(int a) {
                double result = sqrt(a*100);
                return result;
            }
        };

        formula1.calculate(100);
        formula1.sqrt(-22);
        Formula.positive(-4);

//        Formula formula2 = (a) -> sqrt(a*100);
    }
}
