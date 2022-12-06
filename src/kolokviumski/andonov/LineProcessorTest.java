package kolokviumski.andonov;

//implement class LineProcessor with methods:
//void readLines(Inputstream is, Outputstream os, char ac)
//which will receive strings and output the string
//that has the character the most amount of times
//if there are multiple string, output the last one.
//no case-sensitivity
//using streams makes it much easier!

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

//your code here
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
//        for(char character:line.toLowerCase().toCharArray()){
//            if(character == this.c){
//                counter++;
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public int compareTo(Line line) {
//        return Integer.compare(this.countOcc(), line.countOcc());
//    }
//
//    @Override
//    public String toString() {
//        return line;
//    }
//}

class LineProcessor{
    //List<Line> lines;
    List<String> lines;

    public LineProcessor() {
        lines = new ArrayList<>();
    }

    private int countOcc(String line, char c){
//        int counter = 0;
//        for(char character: line.toLowerCase().toCharArray()){
//            if(character == c){
//                ++counter;
//            }
//        }
//        return counter;

        //with streams
        return (int)line.toLowerCase()
                .chars()
                .filter(i-> (char)i == c)
                .count();
    }

    void readLines(InputStream in, OutputStream out, char c){
        //Scanner sc = new Scanner(in);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));

        lines = br.lines().collect(Collectors.toList());

        Comparator<String> comparator = Comparator.comparing(str -> countOcc(str,c));

        String max = lines.stream()
                .max(comparator.thenComparing(Comparator.naturalOrder()))
                .orElse(""); //exception prevention


        PrintWriter pw = new PrintWriter(out);

//        while(sc.hasNextLine()){
//            String str = sc.nextLine();
//            Line line = new Line(str, c);
//            lines.add(line);
//        }
//        sc.close();

//        Line max = lines.get(0); //first line read from in
//        for(Line line: lines){
//            if(line.compareTo(max)>=0){ //if line has more occurrences of character than the last line, replace
//                max = line;
//            }
//        }

        pw.println(max);
        pw.flush();
    }
}

//starter
public class LineProcessorTest{
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

            lineProcessor.readLines(System.in, System.out, 'a');
    }
}