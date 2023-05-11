package ispitni_re;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class Names{
    Map<String,Integer> namesFrequencyMap;

    public Names() {
        namesFrequencyMap = new HashMap<>();
    }

    public void addName(String name) {
        namesFrequencyMap.computeIfPresent(name, (k,v) -> ++v);
        namesFrequencyMap.putIfAbsent(name,1);
    }

    public int calcUniqueChars(String name){
        char[] arr = name.toLowerCase().toCharArray();
        Set<Character> charSet = new HashSet<>();
        for(char c : arr)
            charSet.add(c);
        return charSet.size();
    }

    public void printN(int n) {
        namesFrequencyMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= n)
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry -> System.out.printf(
                        "%s (%d) %d\n",
                        entry.getKey(),entry.getValue(),
                        calcUniqueChars(entry.getKey())
                ));
    }

    public String findName(int len, int x) {
        List<String> names = namesFrequencyMap.keySet().stream()
                .filter(name -> name.length() < len)
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        if(x >= names.size())
            x = x % names.size(); //wow

        return names.get(x);
    }
}

public class NamesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        Names names = new Names();
        for (int i = 0; i < n; ++i) {
            String name = scanner.nextLine();
            names.addName(name);
        }
        n = scanner.nextInt();
        System.out.printf("===== PRINT NAMES APPEARING AT LEAST %d TIMES =====\n", n);
        names.printN(n);
        System.out.println("===== FIND NAME =====");
        int len = scanner.nextInt();
        int index = scanner.nextInt();
        System.out.println(names.findName(len, index));
        scanner.close();

    }
}
