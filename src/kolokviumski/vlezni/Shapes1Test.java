package kolokviumski.vlezni;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Square implements Comparable<Square>{
    int side;

    public Square(int side) {
        this.side = side;
    }

    public int perimeter(){
        return 4 * side;
    }

    @Override
    public int compareTo(Square o) {
        return Integer.compare(this.perimeter(), o.perimeter());
    }
}

class CanvasFactory{

    public static Canvas createCanvas(String line) {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Square> squares;

        squares = Arrays.stream(parts)
                .skip(1)
                .map(part -> new Square(Integer.parseInt(part)))
                .collect(Collectors.toList());

        return new Canvas(id, squares);
    }
}

class Canvas{
    String id;
    List<Square> squares;

    public Canvas(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }

    public int squareCount(){
        return squares.size();
    }

    public int sumPerimeter(){
        return squares.stream()
                .mapToInt(Square::perimeter)
                .sum();
    }

    @Override
    public String toString() {
        return String.format("%s %d %d",
                id, squares.size(), sumPerimeter());
    }
}

class ShapesApplication{
    List<Canvas> canvases;

    public int readCanvases(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        this.canvases = br.lines()
                .map(CanvasFactory::createCanvas)
                .collect(Collectors.toList());

        return canvases.stream()
                .mapToInt(Canvas::squareCount)
                .sum();
        //return canvases.stream().count(); also works
    }

    private int findLargest(){
        int max = canvases.get(0).sumPerimeter();
        int index = 0;
        for(int i=1;i<canvases.size();i++){
            if(canvases.get(i).sumPerimeter() > max){
                max = canvases.get(i).sumPerimeter();
                index = i;
            }
        }
        return index;
    }

    public void printLargestCanvasTo(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        int max = findLargest();
        Canvas maxCanvas = canvases.get(max);

        pw.println(maxCanvas);
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