//package ispitni_re.old_exams;
//
//import java.time.LocalDateTime;
//import java.util.*;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//enum COMPARATOR_TYPE {
//    NEWEST_FIRST,
//    OLDEST_FIRST,
//    LOWEST_PRICE_FIRST,
//    HIGHEST_PRICE_FIRST,
//    MOST_SOLD_FIRST,
//    LEAST_SOLD_FIRST
//}
//
//class ProductNotFoundException extends Exception {
//    ProductNotFoundException(String message) {
//        super(String.format("Product with id %s does not exist in the online shop!", message));
//    }
//}
//
//
//class Product {
//    String category;
//    String id;
//    String name;
//    LocalDateTime dateAdded;
//    double price;
//    int timesSold;
//
//    public Product(String category, String id, String name, LocalDateTime dateAdded, double price) {
//        this.category = category;
//        this.id = id;
//        this.name = name;
//        this.dateAdded = dateAdded;
//        this.price = price;
//        timesSold = 0;
//    }
//
//    public String getCategory() {
//        return category;
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
//    public LocalDateTime getDateAdded() {
//        return dateAdded;
//    }
//
//    public double getPrice() {
//        return price;
//    }
//
//    public int getTimesSold() {
//        return timesSold;
//    }
//
//    @Override
//    public String toString() {
//        return "Product{" +
//                "id='" + id + '\'' +
//                ", name='" + name + '\'' +
//                ", createdAt=" + dateAdded +
//                ", price=" + price +
//                ", quantitySold=" + timesSold +
//                '}';
//    }
//}
//
//
//class OnlineShop {
//    public Map<String,Product> products;
//
//    OnlineShop() {
//        products = new HashMap<>();
//    }
//
//    void addProduct(String category, String id, String name, LocalDateTime createdAt, double price){
//        products.putIfAbsent(id, new Product(category,id,name,createdAt,price));
//    }
//
//    double buyProduct(String id, int quantity) throws ProductNotFoundException{
//        double moneySpent = 0.0;
//        if(products.get(id) == null)
//            throw new ProductNotFoundException(id);
//        else {
//            moneySpent += products.get(id).price * quantity;
//            products.get(id).timesSold += quantity;
//        }
//        return moneySpent;
//    }
//
//    List<List<Product>> listProducts(String category, COMPARATOR_TYPE comparatorType, int pageSize) {
//        Comparator<Product> comparator;
//        switch (comparatorType) {
//            case NEWEST_FIRST: comparator = Comparator.comparing(Product::getDateAdded).reversed(); break;
//            case OLDEST_FIRST: comparator = Comparator.comparing(Product::getDateAdded); break;
//            case LOWEST_PRICE_FIRST: comparator = Comparator.comparing(Product::getPrice); break;
//            case HIGHEST_PRICE_FIRST: comparator = Comparator.comparing(Product::getPrice).reversed(); break;
//            case MOST_SOLD_FIRST: comparator = Comparator.comparing(Product::getTimesSold).reversed(); break;
//            case LEAST_SOLD_FIRST: comparator = Comparator.comparing(Product::getTimesSold); break;
//            default: throw new IllegalArgumentException();
//        }
//
//        List<Product> productList = new ArrayList<>(products.values());
//        if(category != null){
//            productList = productList.stream()
//                    .filter(i -> i.getCategory().equals(category))
//                    .collect(Collectors.toList());
//        }
//        productList.sort(comparator);
//
//        final List<Product> finalList = productList;
//
//        return IntStream.range(0, (productList.size() + pageSize - 1) / pageSize)
//                .mapToObj(i -> finalList.subList(i * pageSize, Math.min((i + 1) * pageSize, finalList.size())))
//                .collect(Collectors.toList());
//    }
//
//}
//
//public class OnlineShopTest {
//
//    public static void main(String[] args) {
//        OnlineShop onlineShop = new OnlineShop();
//        double totalAmount = 0.0;
//        Scanner sc = new Scanner(System.in);
//        String line;
//        while (sc.hasNextLine()) {
//            line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//            if (parts[0].equalsIgnoreCase("addproduct")) {
//                String category = parts[1];
//                String id = parts[2];
//                String name = parts[3];
//                LocalDateTime createdAt = LocalDateTime.parse(parts[4]);
//                double price = Double.parseDouble(parts[5]);
//                onlineShop.addProduct(category, id, name, createdAt, price);
//            } else if (parts[0].equalsIgnoreCase("buyproduct")) {
//                String id = parts[1];
//                int quantity = Integer.parseInt(parts[2]);
//                try {
//                    totalAmount += onlineShop.buyProduct(id, quantity);
//                } catch (ProductNotFoundException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else {
//                String category = parts[1];
//                if (category.equalsIgnoreCase("null"))
//                    category=null;
//                String comparatorString = parts[2];
//                int pageSize = Integer.parseInt(parts[3]);
//                COMPARATOR_TYPE comparatorType = COMPARATOR_TYPE.valueOf(comparatorString);
//                printPages(onlineShop.listProducts(category, comparatorType, pageSize));
//            }
//        }
//        System.out.println("Total revenue of the online shop is: " + totalAmount);
//
//    }
//
//    private static void printPages(List<List<Product>> listProducts) {
//        for (int i = 0; i < listProducts.size(); i++) {
//            System.out.println("PAGE " + (i + 1));
//            listProducts.get(i).forEach(System.out::println);
//        }
//    }
//}
//
