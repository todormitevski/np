//package kolokviumski.vlezni;
//
//import java.io.*;
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
//        for(char character: line.toLowerCase().toCharArray()){
//            if(character == this.c){
//                ++counter;
//            }
//        }
//        return counter;
//    }
//
//    @Override
//    public int compareTo(Line o) {
//        return Integer.compare(this.countOcc(), o.countOcc());
//    }
//}
//
//class LineProcessor{
//
//    public void readLines(InputStream is, OutputStream os, char c) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//
//    }
//}
//
//public class LineProcessorTest {
//    public static void main(String[] args) {
//        LineProcessor lineProcessor = new lineProcessor();
//
//        try{
//            lineProcessor.readLines(System.in, System.out, 'a');
//        } catch(IOException e){
//            e.printStackTrace();
//        }
//    }
//}
