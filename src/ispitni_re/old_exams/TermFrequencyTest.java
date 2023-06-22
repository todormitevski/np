package ispitni_re.old_exams;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;
import java.util.stream.Collectors;

class TermFrequency{
    List<String> wordsToSkip;
    Map<String,Integer> wordFrequencyMap = new TreeMap<>();

    TermFrequency(InputStream inputStream, String[] stopWords){
        wordsToSkip = List.of(stopWords);
        Scanner sc = new Scanner(inputStream);
        while(sc.hasNext()){
            String line = sc.nextLine();
            line = line.trim();
            if(line.length() > 0){
                String[] parts = line.split("\\s+");
                for(String part : parts){
                    part = part.replace(",","").replace(".","").toLowerCase().trim();
                    if(!wordsToSkip.contains(part) && !part.equals("")){
                        wordFrequencyMap.putIfAbsent(part,1);
                        wordFrequencyMap.computeIfPresent(part, (k,v) -> v+1);
                    }
                }
            }
        }
    }

    public int countTotal() {
        return wordFrequencyMap.values().stream()
                .mapToInt(Integer::intValue)
                .sum();
    }

    public int countDistinct() {
        return wordFrequencyMap.keySet().size();
    }

    public List<String> mostOften(int k) {
        return wordFrequencyMap.entrySet().stream()
                .sorted(Entry.<String,Integer>comparingByValue().reversed()
                        .thenComparing(Entry.comparingByKey()))
                .limit(k)
                .map(Entry::getKey)
                .collect(Collectors.toList());
    }
}

public class TermFrequencyTest {
    public static void main(String[] args) throws FileNotFoundException {
        String[] stop = new String[] { "во", "и", "се", "за", "ќе", "да", "од",
                "ги", "е", "со", "не", "тоа", "кои", "до", "го", "или", "дека",
                "што", "на", "а", "но", "кој", "ја" };
        TermFrequency tf = new TermFrequency(System.in,
                stop);
        System.out.println(tf.countTotal());
        System.out.println(tf.countDistinct());
        System.out.println(tf.mostOften(10));
    }
}
// vasiot kod ovde
