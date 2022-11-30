package vezbi.audReRun.aud3.lambda;

public class LambdaTest {
    public static void main(String[] args) {
        FunctionalInterface f1 = (x, y) -> {
            System.out.println("yeah");
            x++;
            return x + y;
        };

        FunctionalInterface f2 = (x,y) -> x * y;
    }
}
