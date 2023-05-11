package auds.aud8;

import java.util.*;

public class SetAndMapIntro {
    public static void main(String[] args) {
        //get O(log_n), iterating O(nlog_n), addition O(log_n), deletion O(nlog_n)
        Set<Integer> treeIntSet = new TreeSet<>(Comparator.reverseOrder()); //has to have comparable elems
        for(int i=1;i<=10;i++){
            treeIntSet.add(i);
            treeIntSet.add(i); //doesn't do anything because treeSet only takes unique elems
        }
        System.out.println(treeIntSet);

        //doesn't keep order for objects
        Set<Integer> hashIntSet = new HashSet<>(); //complexity O(1), adding n elems is O(n)
        for(int i=1;i<=10;i++){
            hashIntSet.add(i);
            hashIntSet.add(i);
        }
        System.out.println(hashIntSet);

        Set<String> hashStringSet = new HashSet<>();
        hashStringSet.add("FINKI");
        hashStringSet.add("finki");
        hashStringSet.add("NP");
        hashStringSet.add("Napredno");
        System.out.println(hashStringSet); //not for keeping order
        //use LinkedHashSet for keeping order
        //treeSet sorts, doesn't keep order

        Set<String> linkedHashStringSet = new LinkedHashSet<>();
        linkedHashStringSet.add("FINKI");
        linkedHashStringSet.add("finki");
        linkedHashStringSet.add("NP");
        linkedHashStringSet.add("Napredno");
        System.out.println(linkedHashStringSet);

        //summary
            //all Sets are used to avoid duplicates
            //TreeSet is used to sort elems by comparator
            //HashSet for simplest time complexity with no dupes
            //LinkedHashSet simple time complex, keep order
    }
}
