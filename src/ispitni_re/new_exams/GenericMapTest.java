package ispitni_re.new_exams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

interface MergeStrategy<T>{
    T mergeValues(T val1, T val2);
}

class IntegerSumMergeStrategy implements MergeStrategy<Integer>{

    @Override
    public Integer mergeValues(Integer val1, Integer val2) {
        return val1 + val2;
    }
}

class StringMergeStrategy implements MergeStrategy<String>{

    @Override
    public String mergeValues(String val1, String val2) {
        return val1.concat(val2);
    }
}

class MaxIntegerMergeStrategy implements MergeStrategy<Integer>{

    @Override
    public Integer mergeValues(Integer val1, Integer val2) {
        return Math.max(val1,val2);
    }
}

class SubstringMergeStrategy implements MergeStrategy<String>{

    @Override
    public String mergeValues(String val1, String val2) {
        return val1.replaceAll(val2, "*".repeat(val2.length()));
    }
}

class MapOps{
    public static <K,V> Map<K,V> merge(Map<K,V> map1, Map<K,V> map2, MergeStrategy<V> strategy){
        Map<K,V> result = new TreeMap<>();

        for(K key : map1.keySet()){
            if(map2.containsKey(key)){
                V val1 = map1.get(key);
                V val2 = map2.get(key);
                V mergedVal = strategy.mergeValues(val1,val2);
                result.put(key,mergedVal);
                map2.remove(key);
            } else{
                V val1 = map1.get(key);
                result.put(key,val1);
            }
        }

        for(K key : map2.keySet()){
            V val2 = map2.get(key);
            result.put(key,val2);
        }

        return result;
    }
}

public class GenericMapTest {
    @SuppressWarnings({"unsafe","unchecked"})
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int testCase = Integer.parseInt(sc.nextLine());

        if (testCase == 1) { //Mergeable integers
            Map<Integer, Integer> mapLeft = new HashMap<>();
            Map<Integer, Integer> mapRight = new HashMap<>();
            readIntMap(sc, mapLeft);
            readIntMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two Integer objects into a new Integer object which is their sum
            MergeStrategy mergeStrategy = new IntegerSumMergeStrategy();

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 2) { // Mergeable strings
            Map<String, String> mapLeft = new HashMap<>();
            Map<String, String> mapRight = new HashMap<>();
            readStrMap(sc, mapLeft);
            readStrMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two String objects into a new String object which is their concatenation
            MergeStrategy mergeStrategy = new StringMergeStrategy();

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 3) {
            Map<Integer, Integer> mapLeft = new HashMap<>();
            Map<Integer, Integer> mapRight = new HashMap<>();
            readIntMap(sc, mapLeft);
            readIntMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two Integer objects into a new Integer object which will be the max of the two objects
            MergeStrategy mergeStrategy = new MaxIntegerMergeStrategy();

            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        } else if (testCase == 4) {
            Map<String, String> mapLeft = new HashMap<>();
            Map<String, String> mapRight = new HashMap<>();
            readStrMap(sc, mapLeft);
            readStrMap(sc, mapRight);

            //TODO Create an object of type MergeStrategy that will enable merging of two String objects into a new String object which will mask the occurrences of the second string in the first string

            MergeStrategy mergeStrategy = new SubstringMergeStrategy();
            printMap(MapOps.merge(mapLeft, mapRight, mergeStrategy));
        }
    }

    private static void readIntMap(Scanner sc, Map<Integer, Integer> map) {
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            int k = Integer.parseInt(parts[0]);
            int v = Integer.parseInt(parts[1]);
            map.put(k, v);
        }
    }

    private static void readStrMap(Scanner sc, Map<String, String> map) {
        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            map.put(parts[0], parts[1]);
        }
    }

    private static void printMap(Map<?, ?> map) {
        map.forEach((k, v) -> System.out.printf("%s -> %s%n", k.toString(), v.toString()));
    }
}
