package ispitni_re;

import java.io.*;
import java.util.*;

//ANOTHER TIME

class CosineSimilarityCalculator {
    public static double cosineSimilarity (Collection<Integer> c1, Collection<Integer> c2) {
        int [] array1;
        int [] array2;
        array1 = c1.stream().mapToInt(i -> i).toArray();
        array2 = c2.stream().mapToInt(i -> i).toArray();
        double up = 0.0;
        double down1=0, down2=0;

        for (int i=0;i<c1.size();i++) {
            up+=(array1[i] * array2[i]);
        }

        for (int i=0;i<c1.size();i++) {
            down1+=(array1[i]*array1[i]);
        }

        for (int i=0;i<c1.size();i++) {
            down2+=(array2[i]*array2[i]);
        }

        return up/(Math.sqrt(down1)*Math.sqrt(down2));
    }
}

class Word{
    String word;

    public Word(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    @Override
    public String toString() {
        return word;
    }
}

class TextProcessor{
    List<String> lines;
    Map<Word,Integer> words;

    public TextProcessor() {
        lines = new ArrayList<>();
        words = new TreeMap<>(Comparator.comparing(Word::getWord));
    }

    public String normalizeLine(String line){
        return line.trim().replaceAll("[^a-zA-Z\\s]+", "");
    }

    public void readText(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    lines.add(normalizeLine(line));
                });
        lines.forEach(line ->{
            String[] parts = line.split("\\s+");
            for(String part : parts){
                words.putIfAbsent(new Word(part),0);
            }
        });
    }

    public void printTextsVectors(PrintStream printStream) {
        PrintWriter pw = new PrintWriter(printStream);
        StringJoiner sj = new StringJoiner(", ");
        lines.forEach(line ->{
            String[] parts = line.split("\\s+");
            for(String part : parts){
                Word word = new Word(part);
                words.computeIfPresent(word, (k,v) -> ++v);
            }
            words.values().forEach(value -> sj.add(String.valueOf(value)));
            pw.println("[" + sj + "]");
        });
        pw.flush();
    }
}

public class TextProcessorTest {

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();

        textProcessor.readText(System.in);

        System.out.println("===PRINT VECTORS===");
        textProcessor.printTextsVectors(System.out);

        System.out.println("PRINT FIRST 20 WORDS SORTED ASCENDING BY FREQUENCY ");
        //textProcessor.printCorpus(System.out,  20, true);

        System.out.println("PRINT FIRST 20 WORDS SORTED DESCENDING BY FREQUENCY");
        //textProcessor.printCorpus(System.out, 20, false);

        System.out.println("===MOST SIMILAR TEXTS===");
        //textProcessor.mostSimilarTexts(System.out);
    }
}