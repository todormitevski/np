//package ispitni_re;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//class Canvas{
//    String id;
//    List<Integer> sizes;
//    int n;
//
//    public Canvas(String id, List<Integer> sizes) {
//        this.id = id;
//        this.sizes = sizes;
//        this.n = sizes.size();
//    }
//
//    public int calcPerimeter(){
//        int perimeter = 0;
//        for(int square : sizes){
//            perimeter += square * 4;
//        }
//        return perimeter;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public List<Integer> getSizes() {
//        return sizes;
//    }
//
//    public int getN() {
//        return n;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %d",id,n,calcPerimeter());
//    }
//}
//
//class ShapesApplication{
//    List<Canvas> canvases;
//
//    public ShapesApplication() {
//        canvases = new ArrayList<Canvas>();
//    }
//
//    public int readCanvases(InputStream inputStream) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        br.lines()
//                .forEach(line ->{
//                    List<Integer> sizes = new ArrayList<Integer>();
//                    String[] parts = line.split(" ");
//                    sizes = Arrays.stream(parts)
//                            .skip(1)
//                            .map(Integer::parseInt)
//                            .collect(Collectors.toList());
//                    canvases.add(new Canvas(parts[0],sizes));
//                });
//        int counter = 0;
//        for (Canvas canvas : canvases){
//            counter += canvas.getN();
//        }
//        return counter;
//    }
//
//
//    public void printLargestCanvasTo(PrintStream printStream) {
//        PrintWriter pw = new PrintWriter(printStream);
//        canvases.sort(Comparator.comparing(Canvas::calcPerimeter).reversed());
//        pw.println(canvases.get(0));
//        pw.close();
//    }
//}
//
//public class Shapes1Test {
//
//    public static void main(String[] args) {
//        ShapesApplication shapesApplication = new ShapesApplication();
//
//        System.out.println("===READING SQUARES FROM INPUT STREAM===");
//        System.out.println(shapesApplication.readCanvases(System.in));
//        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
//        shapesApplication.printLargestCanvasTo(System.out);
//
//    }
//}