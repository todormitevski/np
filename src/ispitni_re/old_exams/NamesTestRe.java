package ispitni_re.old_exams;

import java.util.*;
import java.util.stream.Collectors;

class Names{
    Map<String,Integer> names;

    public Names() {
        names = new TreeMap<>(); //sorts automatically
    }

    public void addName(String name){
        names.computeIfPresent(name, (k,v) -> ++v);
        names.putIfAbsent(name,1);
    }

    public int countUniqueLetters(String name){
        Set<Character> charSet = new HashSet<>();
        for(Character c : name.toCharArray()){
            charSet.add(Character.toLowerCase(c));
        }
        return charSet.size();
    }

    public void printN(int n){
        StringJoiner sj = new StringJoiner("\n");
        names.entrySet().stream()
                .filter(entry -> entry.getValue() >= n)
                .forEach(entry -> {
                    String name = entry.getKey();
                    int count = entry.getValue();
                    sj.add(String.format(
                            "%s (%d) %d",
                            name,count,countUniqueLetters(name)
                    ));
                });
        System.out.println(sj);
    }

    public String findName(int len, int x){
        List<String> filteredNames = names.keySet().stream()
                .filter(key -> key.length() < len)
                .collect(Collectors.toList());

        int size = filteredNames.size();
        if (size == 0) {
            return "";
        }
        int index = x % size;
        return filteredNames.get(index);
    }
}

public class NamesTestRe {
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

// vashiot kod ovde