package kolokviumski.rerun;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

// not needed if using streams
//class Line implements Comparable<Line>{
//    String line;
//    char c;
//
//    public Line(String line, char c) {
//        this.line = line;
//        this.c = c;
//    }
//
//    private int countOcc(){
//        int counter = 0;
//        for(char character: line.toLowerCase().toCharArray()){
//            if(character == this.c){
//                counter++;
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public String toString() {
//        return line;
//    }
//
//    @Override
//    public int compareTo(Line line) {
//        return Integer.compare(this.countOcc(), line.countOcc());
//    }
//}

class LineProcessor{
    List<String> lines;

    public LineProcessor() {
        lines = new ArrayList<>();
    }

    private int countOcc(String line, char c){
        int counter = 0;
        for(char character: line.toLowerCase().toCharArray()){
            if(character == c){
                ++counter;
            }
        }
        return counter;
    }

    public void readLines(InputStream in, OutputStream out, char c) {
        // noob method
//        Scanner sc = new Scanner(in);
//        PrintWriter pw = new PrintWriter(out);
//
//        while(sc.hasNextLine()){
//            String str = sc.nextLine();
//            Line line = new Line(str, c);
//            lines.add(line);
//        }
//
//        sc.close();
//
//        Line max = lines.get(0);
//        for (Line line: lines){
//            if(line.compareTo(max) >= 0){ //last one, > for first one
//                max = line;
//            }
//        }

        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(out);

        lines = br.lines().collect(Collectors.toList());
        Comparator <String> comparator = Comparator.comparing(str -> countOcc(str,c));
        String max = lines.stream()
                .max(comparator.thenComparing(Comparator.naturalOrder()))
                .orElse("");

        pw.println(max);
        pw.flush();
    }


}

public class LineProcessorTest {
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        lineProcessor.readLines(System.in, System.out, 'a');
    }
}
