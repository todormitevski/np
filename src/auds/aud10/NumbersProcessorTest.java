package auds.aud10;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

class Row{
    List<Integer> numbers;

    public Row(String line) {
        this.numbers = Arrays.stream(line.split(" "))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }

    public boolean checkFrequency(){
        //checks if the number is the highest and most frequent in the list
        int max = numbers.stream().mapToInt(i -> i).max().getAsInt();
        Map<Integer,Long> frequencyMap = numbers.stream()
                .collect(Collectors.groupingBy(
                        i -> i,
                        Collectors.counting()
                ));
        int frequencyOfMaxNumber = frequencyMap.get(max).intValue();
        int maxFrequency = frequencyMap.values().stream()
                .mapToInt(Long::intValue)
                .max()
                .getAsInt();
        return maxFrequency == frequencyOfMaxNumber;
    }

    public int maxNumber(){
        return numbers.stream()
                .mapToInt(i -> i)
                .max()
                .getAsInt();
    }
}

class NumbersProcessor{
    List<Row> rows;

    public NumbersProcessor() {
        rows = new ArrayList<Row>();
    }


    public void readRows(InputStream in) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        rows = br.lines()
                .map(Row::new)
                .collect(Collectors.toList());
    }

    public void printMaxFromRows(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        rows.stream()
                .filter(Row::checkFrequency)
                .map(Row::maxNumber)
                .forEach(pw::println);

        pw.flush();
        pw.close();
    }
}

public class NumbersProcessorTest {
    public static void main(String[] args) {
        NumbersProcessor numbersProcessor = new NumbersProcessor();
        numbersProcessor.readRows(System.in);
        numbersProcessor.printMaxFromRows(System.out);
    }
}
