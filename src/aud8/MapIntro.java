package aud8;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class MapIntro {
    public static void main(String[] args) {
        //key has to be comparable, avoids dupes
        //map is sorted by key,
        //complexity: addition O(log_n), contain O(log_n),
        //iterating O(nlog_n)
        Map<String,String> treeMap = new TreeMap<>(/*comp*/);

        treeMap.put("FINKI", "FINKI");
        treeMap.put("Finki", "Finki");
        treeMap.put("NP", "Napredno programiranje");
        treeMap.put("F", "Fakultet");
        treeMap.put("I", "Intformaticki");
        treeMap.put("F", "Fakultetttt"); //overwrites last F key

        System.out.println(treeMap);


        //HashMap
        //O(1) addition, O(1) contains, O(n) iterating
        //bugs order
        //key elems need to have overridden hashcode method
        Map<String,String> hashMap = new HashMap<>();

        hashMap.put("FINKI", "FINKI");
        hashMap.put("Finki", "Finki");
        hashMap.put("NP", "Napredno programiranje");
        hashMap.put("F", "Fakultet");
        hashMap.put("I", "Intformaticki");
        hashMap.put("F", "Fakultetttt");

        System.out.println(hashMap);


        //LinkedHashmap
        //same complexity with HashMap
        //but LinkedHashMap keeps order
        Map<String,String> linkedHashMap = new LinkedHashMap<>();

        linkedHashMap.put("FINKI", "FINKI");
        linkedHashMap.put("Finki", "Finki");
        linkedHashMap.put("NP", "Napredno programiranje");
        linkedHashMap.put("F", "Fakultet");
        linkedHashMap.put("I", "Intformaticki");
        linkedHashMap.put("F", "Fakultetttt");

        System.out.println(linkedHashMap);

        //maps used for counting elems
        //maps used for grouping elems
    }
}
