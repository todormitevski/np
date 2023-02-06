package ispitni;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIdException extends Exception{
    public InvalidIdException(String message) {
        System.out.printf("ID %s is not valid\n", message);
    }
}

class InvalidDimensionException extends Exception{
    public InvalidDimensionException() {
        System.out.println("Dimension 0 is not allowed!");
    }
}

interface Shape{
    double area();
    double perimeter();
    void scale(double coefficient);
}

class Circle implements Shape{
    double radius;
    static double PI = Math.PI;

    public Circle(double radius) {
        this.radius = radius;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public double area(){
        return PI * radius * radius;
    }

    public double perimeter(){
        return 2 * PI * radius;
    }

    @Override
    public void scale(double coefficient) {
        radius *= coefficient;
    }

    @Override
    public String toString() {
        return String.format(
                "Circle -> Radius: %.2f Area: %.2f Perimeter: %.2f",
                radius,area(),perimeter()
                );
    }
}

class Square implements Shape{
    double side;

    public Square(double side) {
        this.side = side;
    }

    public double getSide() {
        return side;
    }

    public void setSide(double side) {
        this.side = side;
    }

    public double area(){
        return side * side;
    }

    public double perimeter(){
        return side + side + side + side;
    }

    @Override
    public void scale(double coefficient) {
        side *= coefficient;
    }

    @Override
    public String toString() {
        return String.format(
                "Square: -> Side: %.2f Area: %.2f Perimeter: %.2f",
                side,area(),perimeter()
        );
    }
}

class Rectangle implements Shape{
    double a;
    double b;

    public Rectangle(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public double getA() {
        return a;
    }

    public void setA(double a) {
        this.a = a;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double area(){
        return a * b;
    }

    public double perimeter(){
        return 2 * a + 2 * b;
    }

    @Override
    public void scale(double coefficient) {
        a *= coefficient;
        b *= coefficient;
    }

    @Override
    public String toString() {
        return String.format(
                "Rectangle: -> Sides: %.2f, %.2f Area: %.2f Perimeter: %.2f",
                a,b,area(),perimeter()
        );
    }
}

class Canvas{
    Set<Shape> shapes;
    Map<String,Set<Shape>> shapesByUser;

    public Canvas(){
        shapes = new TreeSet<>(Comparator.comparing(Shape::area));
        shapesByUser = new TreeMap<>();
    }

    public void readShapes(InputStream is) throws InvalidIdException, InvalidDimensionException {
        Scanner sc = new Scanner(is);
        while(sc.hasNextLine()){
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");
            int type = Integer.parseInt(parts[0]);
            String userId = parts[1];
            double dimension1 = Double.parseDouble(parts[2]);

            if(dimension1 == 0.0){
                throw new InvalidDimensionException();
            }
            if(userId.length() != 6){
                throw new InvalidIdException(userId);
            }
            for (char c : userId.toCharArray()) {
                if(!Character.isLetterOrDigit(c)){
                    throw new InvalidIdException(userId);
                }
            }

            //1 = circle/2 = square/3 = rectangle
            if (type == 1) {
                Circle circle = new Circle(dimension1);
                shapes.add(circle);
                shapesByUser.putIfAbsent(userId, new TreeSet<>(Comparator.comparing(Shape::perimeter)));
                shapesByUser.get(userId).add(circle);
            } else if (type == 2) {
                Square square = new Square(dimension1);
                shapes.add(square);
                shapesByUser.putIfAbsent(userId, new TreeSet<>(Comparator.comparing(Shape::perimeter)));
                shapesByUser.get(userId).add(square);
            } else if (type == 3) {
                double dimension2 = Double.parseDouble(parts[3]);

                if(dimension2 == 0.0){
                    throw new InvalidDimensionException();
                }

                Rectangle rectangle = new Rectangle(dimension1, dimension2);
                shapes.add(rectangle);
                shapesByUser.putIfAbsent(userId, new TreeSet<>(Comparator.comparing(Shape::perimeter)));
                shapesByUser.get(userId).add(rectangle);
            }
        }
    }

    void scaleShapes (String userID, double coef){
        shapesByUser.getOrDefault(userID,new HashSet<>()).forEach(shape -> shape.scale(coef));
    }

    public void printAllShapes(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        shapes.forEach(pw::println);
        pw.flush();
    }

    void printByUserId (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        Comparator<Map.Entry<String,Set<Shape>>> entryComparator = Comparator.comparing(entry -> entry.getValue().size());

        shapesByUser.entrySet().stream()
                .sorted(entryComparator.reversed().thenComparing(entry -> entry.getValue().stream()
                        .mapToDouble(Shape::area).sum()))
                .forEach(entry -> {
                    pw.println("Shapes of user: " + entry.getKey());
                    entry.getValue().forEach(pw::println);
                });
        pw.flush();
    }

    void statistics (OutputStream os){
        PrintWriter pw = new PrintWriter(os);
        DoubleSummaryStatistics dss = shapes.stream()
                .mapToDouble(Shape::area).summaryStatistics();
        pw.println(String.format("count: %d\nsum: %.2f\nmin: %.2f\naverage: %.2f\nmax: %.2f", dss.getCount(), dss.getSum(), dss.getMin(), dss.getAverage(), dss.getMax()));
        pw.flush();
    }
}

public class CanvasTest {

    public static void main(String[] args){
        Canvas canvas = new Canvas();

        System.out.println("READ SHAPES AND EXCEPTIONS TESTING");
        try {
            canvas.readShapes(System.in);
        } catch (InvalidDimensionException | InvalidIdException e) {
            e.getMessage();
        }
        System.out.println("BEFORE SCALING");
        canvas.printAllShapes(System.out);
        canvas.scaleShapes("123456", 1.5);
        System.out.println("AFTER SCALING");
        canvas.printAllShapes(System.out);

        System.out.println("PRINT BY USER ID TESTING");
        canvas.printByUserId(System.out);

        System.out.println("PRINT STATISTICS");
        canvas.statistics(System.out);
    }
}
