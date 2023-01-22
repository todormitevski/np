package aud9;

import javax.sql.rowset.BaseRowSet;
import java.io.*;
import java.util.*;

public class NamesTest {
    private static Map<String,Integer> readNames(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        Map<String,Integer> result = new HashMap<>();
        br.lines().forEach(
                line ->{
                    String [] parts = line.split("\\s+");
                    String name = parts[0];
                    int freq = Integer.parseInt(parts[1]);
                    result.put(name,freq);
                }
        );
        return result;
    }

    public static void main(String[] args){
        try {
            Map<String,Integer> boyNamesMap = readNames(new FileInputStream("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\aud9\\files\\boynames.txt"));
            Map<String,Integer> girlNamesMap = readNames(new FileInputStream("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\aud9\\files\\girlnames.txt"));

            Set<String> uniqueNames = new HashSet<>();
//            boyNamesMap.keySet().stream()
//                    .filter(name -> girlNamesMap.containsKey(name))
//                    .forEach(name -> uniqueNames.add(name));
//            girlNamesMap.keySet().stream()
//                    .filter(name -> boyNamesMap.containsKey(name))
//                    .forEach(name -> uniqueNames.add(name));

            uniqueNames.addAll(boyNamesMap.keySet());
            uniqueNames.addAll(girlNamesMap.keySet());

            //for alphabetical order sorting
            TreeMap<String,Integer> unisexNameMap = new TreeMap<>(Comparator.reverseOrder());
            uniqueNames.stream()
                    .filter(name -> boyNamesMap.containsKey(name) && girlNamesMap.containsKey(name))
                    .forEach(name -> unisexNameMap.put(name, boyNamesMap.get(name) + girlNamesMap.get(name)));

            //unisexNameMap.forEach((k,v) -> System.out.println(String.format("%s -> %d", k, v)));

            //sort by frequency in descending order
            //common error (ones with same values get replaced, overwritten)
//            TreeMap<Integer,String> namesByFrequency = new TreeMap<>(Comparator.reverseOrder());
//            unisexNameMap.forEach((k,v) -> namesByFrequency.put(v,k));
//            namesByFrequency.forEach((k,v) -> System.out.println(String.format("%s -> %d",v,k)));

            //Set<Map.Entry<String,Integer>> val = unisexNameMap.entrySet();
            unisexNameMap.entrySet().stream()
                    .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                    .forEach(each -> System.out.println(String.format("%s -> %d", each.getKey(), each.getValue())));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


    }
}
