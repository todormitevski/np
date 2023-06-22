//package auds_re.exams;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//class Cake{
//    String name;
//    int price;
//
//    public Cake(String name, int price) {
//        this.name = name;
//        this.price = price;
//    }
//}
//
//class Order{
//    String id;
//    List<Cake> cakes;
//
//    public Order(String line) {
//        String[] parts = line.split("\\s+");
//        List<Cake> cakeList = new ArrayList<>();
//        for(int i=1;i<parts.length;i+=2)
//            cakeList.add(new Cake(parts[i],Integer.parseInt(parts[i+1])));
//        this.id = parts[0];
//        this.cakes = cakeList;
//    }
//
//    public int getNumCakes(){
//        return cakes.size();
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %d",id,getNumCakes());
//    }
//}
//
//class CakeShopApplication{
//    List<Order> orders;
//
//    public CakeShopApplication() {
//        orders = new ArrayList<>();
//    }
//
//    public int readCakeOrders(InputStream inputStream) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        orders = br.lines()
//                .map(Order::new)
//                .collect(Collectors.toList());
//        return orders.stream().mapToInt(Order::getNumCakes).sum();
//    }
//
//    public void printLongestOrder(OutputStream outputStream) {
//        PrintWriter pw = new PrintWriter(outputStream);
//        orders.stream()
//                .max(Comparator.comparing(Order::getNumCakes))
//                .ifPresent(pw::println);
//        pw.flush();
//    }
//}
//
//public class CakeShopApplicationTest1 {
//    public static void main(String[] args) {
//        CakeShopApplication cakeShopApplication = new CakeShopApplication();
//
//        System.out.println("--- READING FROM INPUT STREAM ---");
//        System.out.println(cakeShopApplication.readCakeOrders(System.in));
//
//        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
//        cakeShopApplication.printLongestOrder(System.out);
//    }
//}
