//package kolokviumski;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
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
//        for(char character: line.toLowerCase().toCharArray())
//            if(character == this.c)
//                ++counter;
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
//
//class LineProcessor{
//    //List<Line> lines;
//    List<String> lines;
//
//    public LineProcessor(){
//        lines = new ArrayList<>();
//    }
//
//    private int countOcc(String line, char c){
//        return (int)line.toLowerCase()
//                .chars().filter(i -> ((char)))
//    }
//
////    public int countOcc(String line, char c){
////        int counter = 0;
////        for(char character: line.toLowerCase().toCharArray()){
////            if(character == c){
////                ++counter;
////            }
////        }
////        return counter;
////    }
//
//    public void readLines(InputStream in, PrintStream out, char c){
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//        lines = br.lines().collect(Collectors.toList());
//
//        String max = lines.stream()
//                .max(Comparator.comparingInt(left -> countOcc(left, c).thenComparing(Comparator.ne)))
//        });
////        Scanner sc = new Scanner(in);
////        PrintWriter pw = new PrintWriter(out);
////
////        while(sc.hasNextLine()){
////            String str = sc.nextLine();
////            Line line = new Line(str, c);
////            lines.add(line);
////        }
////        sc.close();
////
////        Line max = lines.get(0);
////        for(Line line: lines){
////            if(line.compareTo(max) >= 0){ //-1 if second is bigger, 0 if they are equal, 1 if first is bigger
////                max = line;
////            }
////        }
////        pw.println(max);
////        pw.flush();
//    }
//}
//
//public class LineProcessorTest {
//    public static void main(String[] args) {
//        LineProcessor lineProcessor = new LineProcessor();
//
//        lineProcessor.readLines(System.in, System.out, 'a');
//    }
//}
