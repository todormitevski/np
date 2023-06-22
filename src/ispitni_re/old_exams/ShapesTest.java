//package ispitni_re.old_exams;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
//too gimmicky
//enum Color {
//    RED, GREEN, BLUE
//}
//
//interface Scalable{
//    void scale(float scaleFactor);
//}
//
//interface Stackable{
//    float weight();
//}
//
//class Shape{
//    String id;
//    Color color;
//
//    public Shape(String id, Color color) {
//        this.id = id;
//        this.color = color;
//    }
//
//    public String getId() {
//        return id;
//    }
//}
//
//class Circle extends Shape implements Scalable, Stackable{
//    float radius;
//
//    public Circle(String id, Color color, float radius) {
//        super(id, color);
//        this.radius = radius;
//    }
//
//    @Override
//    public void scale(float scaleFactor) {
//
//    }
//
//    @Override
//    public float weight() {
//        return 0;
//    }
//}
//
//class Rectangle extends Shape implements Scalable, Stackable{
//    float width;
//    float height;
//
//    public Rectangle(String id, Color color, float width, float height) {
//        super(id, color);
//        this.width = width;
//        this.height = height;
//    }
//
//    @Override
//    public void scale(float scaleFactor) {
//
//    }
//
//    @Override
//    public float weight() {
//        return 0;
//    }
//}
//
//class Canvas{
//    List<Shape> shapes;
//
//    public Canvas() {
//        this.shapes = new ArrayList<>();
//    }
//
//    public void add(String id, Color color, float radius) {
//        shapes.add(new Circle(id,color,radius));
//    }
//
//    public void add(String id, Color color, float width, float height) {
//        shapes.add(new Rectangle(id,color,width,height));
//    }
//
//    public void scale(String id, float scaleFactor) {
//        shapes.stream()
//                .filter(shape -> shape.getId().equals(id))
//                .findFirst()
//                .get()
//                .
//    }
//}
//
//public class ShapesTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        Canvas canvas = new Canvas();
//        while (scanner.hasNextLine()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(" ");
//            int type = Integer.parseInt(parts[0]);
//            String id = parts[1];
//            if (type == 1) {
//                Color color = Color.valueOf(parts[2]);
//                float radius = Float.parseFloat(parts[3]);
//                canvas.add(id, color, radius);
//            } else if (type == 2) {
//                Color color = Color.valueOf(parts[2]);
//                float width = Float.parseFloat(parts[3]);
//                float height = Float.parseFloat(parts[4]);
//                canvas.add(id, color, width, height);
//            } else if (type == 3) {
//                float scaleFactor = Float.parseFloat(parts[2]);
//                System.out.println("ORIGNAL:");
//                System.out.print(canvas);
//                canvas.scale(id, scaleFactor);
//                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
//                System.out.print(canvas);
//            }
//
//        }
//    }
//}
