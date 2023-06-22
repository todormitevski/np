//package ispitni_re.old_exams;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.DoubleSummaryStatistics;
//import java.util.List;
//
//class IrregularCanvasException extends Exception{
//    public IrregularCanvasException(String id, double maxArea) {
//        super(String.format("Canvas %s has a shape with area larger than %.2f",id,maxArea));
//    }
//}
//
//class Square{
//    int side;
//
//    public Square(int side) {
//        this.side = side;
//    }
//
//    public double calcArea(){
//        return side * side;
//    }
//}
//
//class Circle{
//    int radius;
//
//    public Circle(int radius) {
//        this.radius = radius;
//    }
//
//    public double calcArea(){
//        return Math.PI * radius * radius;
//    }
//}
//
//class Window{
//    String id;
//    List<Square> squares;
//    List<Circle> circles;
//    DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
//
//    public Window(String id, List<Square> squares, List<Circle> circles) {
//        this.id = id;
//        this.squares = squares;
//        this.circles = circles;
//        for(Square square : squares)
//            dss.accept(square.calcArea());
//        for(Circle circle : circles)
//            dss.accept(circle.calcArea());
//    }
//
//    public double calcMinArea(){
//        return dss.getMin();
//    }
//
//    public double calcMaxArea(){
//        return dss.getMax();
//    }
//
//    public double calcAverageArea(){
//        return dss.getAverage();
//    }
//
//    public double calcSumArea(){
//        return dss.getSum();
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d %d %d %.2f %.2f %.2f",
//                id,circles.size()+squares.size(),
//                circles.size(),squares.size(),
//                calcMinArea(),calcMaxArea(),calcAverageArea()
//                );
//    }
//}
//
//class ShapesApplication{
//    public List<Window> windows;
//    public static int MAX_AREA;
//
//    public ShapesApplication(int maxArea) {
//        MAX_AREA = maxArea;
//        windows = new ArrayList<Window>();
//    }
//
//    public void readCanvases(InputStream inputStream) throws IrregularCanvasException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        br.lines()
//                .forEach(line ->{
//                    try{
//                        String[] parts = line.split("\\s+");
//                        List<Circle> circles = new ArrayList<Circle>();
//                        List<Square> squares = new ArrayList<Square>();
//                        for(int i=1;i<parts.length;i+=2){
//                            if(parts[i].equals("C")){
//                                Circle circle = new Circle(Integer.parseInt(parts[i+1]));
//                                if(circle.calcArea() > MAX_AREA)
//                                    throw new IrregularCanvasException(parts[0],MAX_AREA);
//                                circles.add(circle);
//                            }
//                            else {
//                                Square square = new Square(Integer.parseInt(parts[i+1]));
//                                if(square.calcArea() > MAX_AREA)
//                                    throw new IrregularCanvasException(parts[0],MAX_AREA);
//                                squares.add(square);
//                            }
//                        }
//                        windows.add(new Window(parts[0],squares,circles));
//                    } catch (IrregularCanvasException e){
//                        System.out.println(e.getMessage());
//                    }
//                });
//    }
//
//    public void printCanvases(PrintStream os) {
//        PrintWriter pw = new PrintWriter(os);
//        windows.stream()
//                .sorted(Comparator.comparing(Window::calcSumArea).reversed())
//                .forEach(pw::println);
//        pw.close();
//    }
//}
//
//public class Shapes2Test {
//
//    public static void main(String[] args) throws IrregularCanvasException {
//
//        ShapesApplication shapesApplication = new ShapesApplication(10000);
//
//        System.out.println("===READING CANVASES AND SHAPES FROM INPUT STREAM===");
//        shapesApplication.readCanvases(System.in);
//        System.out.println("===PRINTING SORTED CANVASES TO OUTPUT STREAM===");
//        shapesApplication.printCanvases(System.out);
//
//
//    }
//}