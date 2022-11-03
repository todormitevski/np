package aud4;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList<>(100);
        List<String> stringList = new ArrayList<>();

        integerList.add(8);
        integerList.add(6);
        integerList.add(2);
        integerList.add(1);
        integerList.add(9);

        stringList.add("A");

        System.out.println(integerList);
        System.out.println(stringList);

        System.out.println(integerList.size());
        System.out.println(integerList.get(3)); //third elem

        System.out.println(integerList.contains(5));
        System.out.println(integerList.contains(102));

        System.out.println(integerList.indexOf(6));
        System.out.println(integerList.lastIndexOf(6));

        System.out.println(integerList.removeIf(i->i>5)); //lambda expression, used when you want to implement a functional interface
        //predicate is a functional interface
        System.out.println(integerList);

        integerList.stream()
                .map()
                .filter()
    }


}
