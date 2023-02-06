package kolokviumski.vlezni;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

class Line implements Comparable<Line>{
    String line;
    char c;

    public Line(String line, char c) {
        this.line = line;
        this.c = c;
    }

    private int countOcc(){
        int counter = 0;
        for(char character : line.toLowerCase().toCharArray()){
            if(character == c){
                counter++;
            }
        }
        return counter;
    }

    @Override
    public int compareTo(Line o) {
        return Integer.compare(this.countOcc(),o.countOcc());
    }

    @Override
    public String toString() {
        return line;
    }
}

class LineProcessor{
    //List<Line> lines;
    List<String> lines;

    public LineProcessor() {
        lines = new ArrayList<>();
    }

    private int countOcc(String line,char c){
        //NORMAL

//        int counter = 0;
//        for(char character : line.toLowerCase().toCharArray()){
//            if(character == c){
//                counter++;
//            }
//        }
//        return counter;

        //STREAMS

        return (int) line.chars()
                .filter(character -> ((char)character == c))
                .count();
    }

    void readLines(InputStream is, OutputStream os, char c){
        //WITH STREAMS AND BR

        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        PrintWriter printWriter = new PrintWriter(os);

        lines = br.lines()
                .collect(Collectors.toList());

        Comparator<String> comparator = Comparator.comparing(line -> countOcc(line,c));

        String max = lines.stream()
                .max(comparator.thenComparing(Comparator.naturalOrder()))
                .get(); //.orElse("") for exception prevention

        printWriter.println(max);
        printWriter.flush();

        //WITH SCANNER

//        Scanner scanner = new Scanner(is);
//        PrintWriter printWriter = new PrintWriter(os);
//
//        while(scanner.hasNextLine()){
//            String input = scanner.nextLine();
//            Line line = new Line(input,c);
//            lines.add(line);
//        }
//
//        Line max = lines.get(0);
//        for(Line line : lines){
//            if(line.compareTo(max) >= 0){
//                max = line;
//            }
//        }
//
//        printWriter.println(max);
//        printWriter.flush();
    }
}

public class LineProcessorTest1{
    public static void main(String[] args) {
        LineProcessor lineProcessor = new LineProcessor();

        lineProcessor.readLines(System.in, System.out, 'a');
    }
}
