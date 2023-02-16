package ispitni.vlezni;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class Canvas{
    String id;
    List<Integer> squares;

    public Canvas(String id, List<Integer> squares) {
        this.id = id;
        this.squares = squares;
    }

    public String getId() {
        return id;
    }

    public List<Integer> getSquares() {
        return squares;
    }

    public int getWindowPerimeter(){
        int sum = 0;
        for(Integer square : squares){
            sum += square * 4;
        }
        return sum;
    }

    @Override
    public String toString() {
        return String.format("%s %d %d",id,squares.size(),getWindowPerimeter());
    }
}

class ShapesApplication{
    List<Canvas> canvases;

    public ShapesApplication() {
        canvases = new ArrayList<Canvas>();
    }

    int readCanvases (InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("\\s+");
                    String id = parts[0];
                    List<Integer> squares = new ArrayList<Integer>();
                    for(int i=1;i< parts.length;i++){
                        squares.add(Integer.parseInt(parts[i]));
                    }
                    Canvas canvas = new Canvas(id,squares);
                    canvases.add(canvas);
                });
        return canvases.stream()
                .mapToInt(windows -> windows.getSquares().size())
                .sum();
    }

    void printLargestCanvasTo (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        canvases.stream()
                .max(Comparator.comparing(Canvas::getWindowPerimeter))
                .ifPresent(System.out::println);
        pw.flush();
    }
}

public class Shapes1Test {

    public static void main(String[] args) {
        ShapesApplication shapesApplication = new ShapesApplication();

        System.out.println("===READING SQUARES FROM INPUT STREAM===");
        System.out.println(shapesApplication.readCanvases(System.in));
        System.out.println("===PRINTING LARGEST CANVAS TO OUTPUT STREAM===");
        shapesApplication.printLargestCanvasTo(System.out);

    }
}
