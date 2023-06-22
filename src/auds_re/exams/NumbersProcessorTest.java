//package auds_re.exams;
//
//import java.io.*;
//import java.util.*;
//import java.util.stream.Collectors;
//
//class Row{
//    Map<Integer,Integer> numFrequencyMap;
//
//    public Row(String line) {
//        numFrequencyMap = new TreeMap<>();
//        Arrays.stream(line.split("\\s+"))
//                .mapToInt(Integer::parseInt)
//                .forEach(num -> {
//                    numFrequencyMap.computeIfPresent(num, (k,v) -> ++v);
//                    numFrequencyMap.putIfAbsent(num, 1);
//                });
//    }
//
//    public int getMaxNum(){
//        return numFrequencyMap.keySet().stream().mapToInt(i -> i).max().getAsInt();
//    }
//
//    public boolean maxNumberIsMostFrequent(){
//        int maxNum = getMaxNum();
//        int mostFreq = numFrequencyMap.values().stream().mapToInt(i -> i).max().getAsInt();
//        return numFrequencyMap.get(maxNum) == mostFreq;
//    }
//}
//
//class NumberProcessor{
//    List<Row> rows;
//
//    public NumberProcessor() {
//        rows = new ArrayList<>();
//    }
//
//    public void readRows(InputStream is) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        rows = br.lines()
//                .map(Row::new)
//                .collect(Collectors.toList());
//    }
//
//
//    public void printMaxFromRows(OutputStream os) {
//        PrintWriter pw = new PrintWriter(os);
//        rows.stream()
//                .filter(Row::maxNumberIsMostFrequent)
//                .forEach(row -> pw.println(row.getMaxNum()));
//        pw.flush();
//    }
//}
//
//public class NumbersProcessorTest {
//    public static void main(String[] args) {
//        NumberProcessor numberProcessor = new NumberProcessor();
//        numberProcessor.readRows(System.in);
//        numberProcessor.printMaxFromRows(System.out);
//    }
//}
