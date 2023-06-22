//package auds_re.exams;
//
//import java.io.*;
//import java.util.ArrayList;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Objects;
//import java.util.stream.Collectors;
//
//class InvalidOrderException extends Exception{
//    public InvalidOrderException(String id) {
//        super(String.format("The order with id %s has less items than the minimum amount",id));
//    }
//}
//
//abstract class Product{
//    String name;
//    int price;
//
//    public Product(String name, int price) {
//        this.name = name;
//        this.price = price;
//    }
//
//    public abstract int getPrice();
//}
//
//class Cake extends Product{
//
//    public Cake(String name, int price) {
//        super(name, price);
//    }
//
//    @Override
//    public int getPrice() {
//        return price;
//    }
//}
//
//class Pie extends Product{
//
//    public Pie(String name, int price) {
//        super(name, price);
//    }
//
//    @Override
//    public int getPrice() {
//        return price + 50;
//    }
//}
//
//class Order{
//    String id;
//    List<Product> products;
//
//    public Order(String line, int minimumProducts) throws InvalidOrderException {
//        String[] parts = line.split("\\s+");
//        List<Product> productList = new ArrayList<>();
//        for(int i=1;i<parts.length-1;i+=2){
//            if(parts[i].charAt(0) == 'C')
//                productList.add(new Cake(parts[i],Integer.parseInt(parts[i+1])));
//            else productList.add(new Pie(parts[i],Integer.parseInt(parts[i+1])));
//        }
//        if(productList.size() < minimumProducts)
//            throw new InvalidOrderException(parts[0]);
//        else{
//            this.id = parts[0];
//            this.products = productList;
//        }
//    }
//
//    public int getSumOfProductPrices(){
//        return products.stream().mapToInt(Product::getPrice).sum();
//    }
//
//    public int getNumPies(){
//        return (int) products.stream()
//                .filter(product -> product.name.charAt(0) == 'P')
//                .count();
//    }
//
//    public int getNumCakes(){
//        return (int) products.stream()
//                .filter(product -> product.name.charAt(0) == 'C')
//                .count();
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "%s %d %d %d %d",
//                id,products.size(),
//                getNumPies(),getNumCakes(),
//                getSumOfProductPrices()
//        );
//    }
//}
//
//class CakeShopApplication{
//    List<Order> orders;
//    int minProductsInOrder;
//
//    public CakeShopApplication(int minProductsInOrder) {
//        this.orders = new ArrayList<>();
//        this.minProductsInOrder = minProductsInOrder;
//    }
//
//    public void readCakeOrders(InputStream inputStream) {
//        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
//        orders = br.lines()
//                .map(line -> {
//                    try {
//                        return new Order(line,minProductsInOrder);
//                    } catch (InvalidOrderException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    return null;
//                })
//                .collect(Collectors.toList());
//    }
//
//    public void printAllOrders(OutputStream outputStream) {
//        PrintWriter pw = new PrintWriter(outputStream);
//        orders.stream()
//                .filter(Objects::nonNull)
//                .sorted(Comparator.comparing(Order::getSumOfProductPrices))
//                .forEach(pw::println);
//        pw.flush();
//    }
//}
//
//public class CakeShopApplicationTest2 {
//    public static void main(String[] args) {
//        CakeShopApplication cakeShopApplication = new CakeShopApplication(4);
//
//        System.out.println("--- READING FROM INPUT STREAM ---");
//        cakeShopApplication.readCakeOrders(System.in);
//
//        System.out.println("--- PRINTING TO OUTPUT STREAM ---");
//        cakeShopApplication.printAllOrders(System.out);
//    }
//}
