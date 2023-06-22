package auds_re.exams;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Row{
    Map<Integer,Integer> numFrequencyMap;

    public Row(String line) {
        numFrequencyMap = new HashMap<>();
        Arrays.stream(line.split("\\s+"))
                .mapToInt(Integer::parseInt)
                .forEach(num -> {
                    numFrequencyMap.computeIfPresent(num, (k,v) -> ++v);
                    numFrequencyMap.putIfAbsent(num, 1);
                });
    }

    public int getMaxNum(){
        return numFrequencyMap.keySet().stream()
                .max(Comparator.comparingInt(Integer::intValue))
                .get();
    }

    public boolean maxNumIsAlsoMostFrequent(){
        return numFrequencyMap.values().stream().mapToInt(i -> i).max().getAsInt() == numFrequencyMap.get(getMaxNum());
    }
}

class NumbersProcessor{
    List<Row> rows;


    public void readRows(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        rows = br.lines()
                .map(Row::new)
                .collect(Collectors.toList());
    }

    public void printMaxFromRows(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        rows.stream()
                .filter(Row::maxNumIsAlsoMostFrequent)
                .forEach(row -> pw.println(row.getMaxNum()));
        pw.flush();
    }
}

public class NumbersProcessorTestRe {
    public static void main(String[] args) {
        NumbersProcessor numberProcessor = new NumbersProcessor();
        numberProcessor.readRows(System.in);
        numberProcessor.printMaxFromRows(System.out);
    }
}