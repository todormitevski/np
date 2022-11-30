//package kolokviumski.kvalifikaciski;
//
//import java.io.BufferedReader;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class Shapes1Test {
//    class Square{
//        int side;
//
//        public Square(int side) {
//            this.side = side;
//        }
//    }
//
//    class Canvas{
//        String canvasID;
//        List<Square> squares;
//
//        public Canvas(String canvasID, List<Square> squares) {
//            this.canvasID = canvasID;
//            this.squares = squares;
//        }
//
//        public static Canvas createCanvas(String line){
//            String [] parts = line.split("\\s+");
//            String id = parts[0];
//
////            List<Square> squares = new ArrayList<>();
////            for(int i=1;i<parts.length;i++){
////                squares.add(new Square(Integer.parseInt(parts[i])));
////            }
//
//            List<Square> squares = Arrays.stream(parts).skip(1)
//                    .mapToInt(part -> Integer.parseInt(part))
//                    .mapToObj(side -> new Square(side))
//                    .collect(Collectors.toList());
//
//        }
//    }
//
//    class ShapesApplication{
//        List<Canvas> canvases;
//
//        public int readCanvases(InputStream in){
//            BufferedReader br = new BufferedReader(new InputStreamReader(in));
//
//            canvases = br.lines()
//                    .map(line -> Canvas.createCanvas(line))
//                    .collect(Collectors.toList());
//            return canvases.stream()
//                    .mapToInt(canvas -> canvas.squares.size())
//                    .
//        }
//    }
//
//
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
