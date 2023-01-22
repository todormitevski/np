package aud7;

import java.util.Arrays;
import java.util.Scanner;
import java.util.stream.Collectors;

public class ArrangeLettersTest {
    public static String arrange(String sentence){
        return Arrays.stream(sentence.split("\\s+"))
                .map(ArrangeLettersTest::arrangeWord)
                .sorted()
                .collect(Collectors.joining(" "));
        // 1. creates stream of [kO, pSk, sO]
        // 2. maps each element using arrangeWord() method
            // turns stream to [Ok, Skp, Os]
        // 3. sorts each element alphabetically
            // turns stream to [Ok, Os, Skp]
        // 4. collects the results, joining the words
            // with an empty space between them
    }

    private static String arrangeWord(String word) {
        // not needed
//        char capital = word.chars()
//                .mapToObj(i -> (char)i)
//                .filter(Character::isUpperCase)
//                .findFirst().get();

        return word.chars()
                .sorted()
                .mapToObj(character -> String.valueOf((char)character))
                .collect(Collectors.joining());
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String sentence = sc.nextLine();
        System.out.println(arrange(sentence));
    }
}
