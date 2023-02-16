package kolokviumski.vlezni;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Square{
    int side;

    public Square(int side) {
        this.side = side;
    }

    public int calcPerimeter(){
        return 4 * side;
    }
}

class Window{
    String id;
    List<Square> squares;

    public Window(String id, List<Square> squares) {
        this.id = id;
        this.squares = squares;
    }

    public List<Square> getSquares() {
        return squares;
    }

    public int calcTotalPerimeter(){
        return squares.stream()
                .mapToInt(Square::calcPerimeter)
                .sum();
    }

    @Override
    public String toString() {
        return String.format(
                "%s %d %d",
                id,squares.size(),calcTotalPerimeter()
        );
    }
}

class ShapesApplication{
    List<Window> windows;

    public ShapesApplication() {
        windows = new ArrayList<>();
    }

    int readCanvases (InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line -> {
                    String[] parts = line.split("\\s+");
                    String id = parts[0];
                    List<Square> squares = new ArrayList<>();
                    for(int i=1;i< parts.length;i++){
                        int side = Integer.parseInt(parts[i]);
                        Square square = new Square(side);
                        squares.add(square);
                    }
                    windows.add(new Window(id,squares));
                });
        return windows.stream()
                .mapToInt(windows -> windows.getSquares().size())
                .sum();
    }

    void printLargestCanvasTo (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        windows.stream()
                .max(Comparator.comparing(Window::calcTotalPerimeter))
                .ifPresent(pw::println);
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