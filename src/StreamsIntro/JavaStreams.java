package StreamsIntro;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class JavaStreams {
    public static void main(String[] args) throws IOException {

        // 1. Integer Stream
        IntStream
                .range(1,10)
                .forEach(System.out::print);
        System.out.println();

        // 2. Integer stream with skip
        IntStream
                .range(1,10)
                .skip(5) //skips first 5 elems
                .forEach(x -> System.out.print(x));
        System.out.println();

        // 3. Integer stream with sum
        int numbers = IntStream
                .range(1,10)
                .sum();
        System.out.print(numbers);
        System.out.println();

        // 4. Stream.of, sorted and findFirst
        Stream.of("Pyke", "Akali", "Shen")
                .sorted()
                .findFirst()
                .ifPresent(System.out::println);
        System.out.println();

        // 5. Stream from Array, sort, filter and print
        String[] names = {"Isaac", "Dan", "Michael", "Trevor", "Franklin", "Jimmy", "Susan", "Sarah", "Simon"};
        Arrays.stream(names)
                .filter(each -> each.startsWith("S"))
                .sorted()
                .forEach(System.out::println);

        // 6. Average squares of int array
        int[] integers = {2,4,6,8,10};
        Arrays.stream(integers)
                .map(each -> each*each)
                .average()
                .ifPresent(System.out::println);

        // 7. Stream from List, filter and print
        List<String> pros = new ArrayList<>();
        pros.add("Zeus");
        pros.add("Oner");
        pros.add("Faker");
        pros.add("Gumayusi");
        pros.add("Keria");

        System.out.println(pros);

        pros.stream()
                .map(String::toLowerCase)
                .filter(each -> each.startsWith("f"))
                .forEach(System.out::println);
        System.out.println();

        // 8. Stream rows from text file, sort, filter, print
        Stream<String> champs = Files.lines(Paths.get("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\StreamsIntro\\champs.txt"));
        champs
                .sorted()
                .filter(each -> each.length() == 4)
                .forEach(System.out::println);
        champs.close();
    }
}
