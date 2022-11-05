package aud4;

import java.util.Random;
import java.util.function.*;

public class FunctionalInterfacesTest {
    public static void main(String[] args) {
        //filter()
        //some logic
        Predicate<Integer> LessThan100 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer<100;
            }
        };

        //lambda
        Predicate<Integer> lessThan100 = integer -> integer < 100;

        //expects no arguments, gives result
        Supplier<Integer> IntegerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(1000);
            }
        };

        //lambda
        Supplier<Integer> integerSupplier = () -> new Random().nextInt(100);

        //doesn't return, but consumes data
        Consumer<String> StringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        //lambda
        Consumer<String> stringConsumer = s -> System.out.println(s); //method reference suggestion

        //receives one value, returns another (type)
        //used in maps
        Function<Integer, String> FormattedNumberString = new Function<Integer, String>() { //inputs integer, returns String
            @Override
            public String apply(Integer integer) {
                return String.format("%d\n", integer);
            }
        };

        //lambda
        Function<Integer, String> formatNumberString = num -> String.format("%d\n", num);

        //receives 2 arguments, returns other value
        BiFunction<Integer, Integer, String> SumNumbersAndFormat = new BiFunction<Integer, Integer, String>() {
            @Override
            public String apply(Integer integer, Integer integer2) {
                return String.format("%d + %d = %d", integer, integer2, integer + integer2);
            }
        };

        BiFunction<Integer, Integer, String> sumNumbersAndFormat =
                (x, y) -> String.format("%d + %d = %d", x, y, x+y);
    }
}
