package kolokviumski;

import java.util.*;

class Names{
    Map<String,Integer> names;

    public Names() {
        names = new TreeMap<>();
    }

    public void addName(String name){
        Integer count = names.get(name); //returns value, aka count of the name
        if(count == null){
            count = 0;
        }
        ++count;
        names.put(name,count);
    }

    public void printN(int n){

    }

    public String findName(int len, int x){
        return null;
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
