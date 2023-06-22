package ispitni_re.new_exams;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import static java.util.Map.Entry;
import java.util.List;
import java.util.ArrayList;

class TermFrequency{
    Map<String,Integer> wordFrequencyMap;

    TermFrequency(InputStream inputStream, String[] stopWords){
        wordFrequencyMap = new HashMap<>();
        List<String> stopWordsList = Arrays.asList(stopWords);
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("\\s+");
                    for (String word : parts){
                        String processedWord = processWord(word);
                        if(!stopWordsList.contains(processedWord) && !processedWord.equals("")){
                            wordFrequencyMap.computeIfPresent(processedWord, (k,v) -> ++v);
                            wordFrequencyMap.putIfAbsent(processedWord, 1);
                        }
                    }
                });
    }

    public String processWord(String word){
        return word.toLowerCase()
                .replace(",","")
                .replace(".","")
                .trim();
    }


    public long countTotal() {
        AtomicInteger sum = new AtomicInteger();
        wordFrequencyMap.values().forEach(sum::addAndGet);
        return sum.get();
    }

    public int countDistinct() {
        return wordFrequencyMap.keySet().size();
    }

    public List<String> mostOften(int k) {
        List<String> mostFrequentWords = new ArrayList<>();
        wordFrequencyMap.entrySet().stream()
                .sorted(Entry.<String,Integer>comparingByValue()
                        .reversed()
                        .thenComparing(Entry.comparingByKey()))
                .limit(k)
                .forEach(entry -> mostFrequentWords.add(entry.getKey()));
        return mostFrequentWords;
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
