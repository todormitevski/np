package aud3.lambda;

public class LambdaTest {

    public static void main(String[] args) {
        FunctionalInterface functionalInterface1 = (x, y) -> {
            System.out.println("test");
            x++;
            return x + y;
        };

        FunctionalInterface functionalInterface2 = (x, y) -> x * y; //receives x & y, returns product of x & y
    }
}
