package aud4;

import java.util.Random;
import java.util.function.*;

public class FunctionalInterfacesTest {
    public static void main(String[] args) {
        Predicate<Integer> LessThan100 = new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) {
                return integer<100;
            }
        };

        Predicate<Integer> lessThan100 = integer -> integer < 100;

        Supplier<Integer> IntegerSupplier = new Supplier<Integer>() {
            @Override
            public Integer get() {
                return new Random().nextInt(1000);
            }
        };

        Supplier<Integer> integerSupplier = () -> new Random().nextInt(100);

        Consumer<String> StringConsumer = new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        };

        Consumer<String> stringConsumer = s -> System.out.println(s); //method reference suggestion

        Function<Integer, String> FormattedNumberString = new Function<Integer, String>() { //inputs integer, returns String
            @Override
            public String apply(Integer integer) {
                return String.format("%d\n", integer);
            }
        };

        Function<Integer, String> formatNumberString = num -> String.format("%d\n", num);

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
