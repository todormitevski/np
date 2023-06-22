//package ispitni_re.new_exams;
//
//import java.io.OutputStream;
//import java.io.PrintWriter;
//import java.util.*;
//
//class InvalidOperationException extends Exception{
//    public InvalidOperationException(String message) {
//        super(message);
//    }
//}
//
//abstract class Product{
//    String id;
//    String name;
//    double price;
//    double quantity;
//    double discount;
//
//    public Product(String id, String name, double price, double quantity) {
//        this.id = id;
//        this.name = name;
//        this.price = price;
//        this.quantity = quantity;
//        discount = 0.0;
//    }
//
//    public String getId() {
//        return id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public abstract double calcPrice();
//
//    public void calcDiscount(double price) {
//        this.discount = price;
//    }
//
//    public double getDiscount() {
//        return discount;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s - %.2f",
//                id,calcPrice());
//    }
//}
//
//class WholeProduct extends Product{
//
//    public WholeProduct(String id, String name, double price, double quantity) {
//        super(id, name, price, quantity);
//    }
//
//    public double calcPrice(){
//        return price * quantity;
//    }
//}
//
//class PartProduct extends Product{
//
//    public PartProduct(String id, String name, double price, double quantity) {
//        super(id, name, price, quantity);
//    }
//
//    public double calcPrice(){
//        return (quantity / 1000) * price;
//    }
//}
//
//class ShoppingCart{
//    List<Product> products;
//    boolean exceptionOccurred = false;
//
//    public ShoppingCart() {
//        products = new ArrayList<>();
//    }
//
//    public void addItem(String itemData) throws InvalidOperationException {
//        String[] parts = itemData.split(";");
//        if(Double.parseDouble(parts[4]) == 0.0){
//            exceptionOccurred = true;
//            throw new InvalidOperationException(String.format(
//                    "The quantity of the product with id %s can not be 0.",
//                    parts[1]
//            ));
//        }
//        if(parts[0].equals("WS")){
//            WholeProduct wp = new WholeProduct(
//                    parts[1],parts[2],
//                    Integer.parseInt(parts[3]),
//                    Double.parseDouble(parts[4])
//            );
//            products.add(wp);
//        } else{
//            PartProduct pp = new PartProduct(
//                    parts[1],parts[2],
//                    Integer.parseInt(parts[3]),
//                    Double.parseDouble(parts[4])
//            );
//            products.add(pp);
//        }
//    }
//
//    public void printShoppingCart(OutputStream os) {
//        PrintWriter pw = new PrintWriter(os);
//        products.stream()
//                .sorted(Comparator.comparing(Product::calcPrice)
//                        .reversed())
//                .forEach(pw::println);
//        pw.flush();
//    }
//
//    public void blackFridayOffer(List<Integer> discountItems, OutputStream os) throws InvalidOperationException {
//        PrintWriter pw = new PrintWriter(os);
//        boolean flag = false;
//        for(Product product : products){
//            if(discountItems.contains(Integer.parseInt(product.getId()))){
//                flag = true;
//                product.calcDiscount(product.calcPrice() * 0.1);
//                pw.println(String.format(
//                        "%s - %.2f",
//                        product.getId(),product.getDiscount()
//                ));
//            }
//        }
//        if((!flag || discountItems.size() == 0) && !exceptionOccurred)
//            throw new InvalidOperationException("There are no products with discount.");
//        pw.flush();
//    }
//}
//
//public class ShoppingTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        ShoppingCart cart = new ShoppingCart();
//
//        int items = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < items; i++) {
//            try {
//                cart.addItem(sc.nextLine());
//            } catch (InvalidOperationException e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        List<Integer> discountItems = new ArrayList<>();
//        int discountItemsCount = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < discountItemsCount; i++) {
//            discountItems.add(Integer.parseInt(sc.nextLine()));
//        }
//
//        int testCase = Integer.parseInt(sc.nextLine());
//        if (testCase == 1) {
//            cart.printShoppingCart(System.out);
//        } else if (testCase == 2) {
//            try {
//                cart.blackFridayOffer(discountItems, System.out);
//            } catch (InvalidOperationException e) {
//                System.out.println(e.getMessage());
//            }
//        } else {
//            System.out.println("Invalid test case");
//        }
//    }
//}