//package auds_re.exams;
//
//import java.util.*;
//
//class UserAlreadyExistsException extends Exception{
//    public UserAlreadyExistsException() {
//        super("User already exists");
//    }
//}
//
//class Order{
//    String pizzaName;
//    float pizzaPrice;
//
//    public Order(String pizzaName, float pizzaPrice) {
//        this.pizzaName = pizzaName;
//        this.pizzaPrice = pizzaPrice;
//    }
//
//    public float getPizzaPrice() {
//        return pizzaPrice;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("%s %.2f", pizzaName, pizzaPrice);
//    }
//}
//
//class User{
//    int id;
//    static int idSeed = 1;
//    String email;
//    String name;
//    String phoneNumber;
//    List<Order> orders;
//
//    public User(String email, String name, String phoneNumber) {
//        id = idSeed++;
//        this.email = email;
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//        orders = new ArrayList<>();
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void addOrder(String pizzaName, float pizzaPrice){
//        orders.add(new Order(pizzaName,pizzaPrice));
//    }
//
//    public List<Order> getOrders() {
//        return orders;
//    }
//}
//
//class PizzaApp{
//    Map<String,User> emailUserMap;
//
//    public PizzaApp() {
//        emailUserMap = new TreeMap<>();
//    }
//
//    public void registerUser(String email, String name, String phoneNumber) throws UserAlreadyExistsException {
//        if(emailUserMap.values().stream().anyMatch(user -> user.getEmail().equals(email)))
//            throw new UserAlreadyExistsException();
//        emailUserMap.putIfAbsent(email,new User(email,name,phoneNumber));
//    }
//
//    public void makeOrder(String email, String pizzaName, float pizzaPrice) {
//        emailUserMap.get(email).addOrder(pizzaName,pizzaPrice);
//    }
//
//    public void printRevenueByPizza() {
//        List<Order> orderList = new ArrayList<>();
//        emailUserMap.values().forEach(user -> orderList.addAll(user.getOrders()));
//        Map<Order,Float> pizzaRevenueMap = new HashMap<>();
//        orderList.forEach(order -> {
//            pizzaRevenueMap.computeIfPresent(order, (k,v) ->{
//                v += order.pizzaPrice;
//                return v;
//            });
//            pizzaRevenueMap.putIfAbsent(order, order.pizzaPrice);
//        });
//        //idk
//        pizzaRevenueMap.entrySet().stream()
//                .sorted(Map.Entry.<Order, Float>comparingByValue()
//                        .thenComparing((Comparator<? super Map.Entry<Order, Float>>) Map.Entry.comparingByKey()))
//                .forEach(entry -> System.out.println(entry.getKey()));
//    }
//
//    public void printMostFrequentUserForPizza() {
//
//    }
//}
//
//public class PizzaAppTest {
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//
//        PizzaApp pizzaApp = new PizzaApp();
//
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//
//            String method = parts[0];
//
//            if(method.equalsIgnoreCase("registerUser")) { //email, name, phone
//                String email = parts[1];
//                String name = parts[2];
//                String phone = parts[3];
//                try {
//                    pizzaApp.registerUser(email, name, phone);
//                } catch (UserAlreadyExistsException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else if (method.equalsIgnoreCase("makeOrder")) { //email, pizzaName, price
//                String email = parts[1];
//                String pizzaName = parts[2];
//                float price = Float.parseFloat(parts[3]);
//                pizzaApp.makeOrder(email, pizzaName, price);
//            } else if (method.equalsIgnoreCase("printRevenueByPizza")) {
//                System.out.println("Print revenue by pizza");
//                pizzaApp.printRevenueByPizza();
//            } else { //printMostFrequentUserForPizza
//                System.out.println("Print most frequent user(s) by pizza");
//
//                pizzaApp.printMostFrequentUserForPizza();
//            }
//        }
//    }
//}
