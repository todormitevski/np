package auds_re.exams;

import java.util.*;

class UserAlreadyExistsException extends Exception{
    public UserAlreadyExistsException() {
        super("User already exists");
    }
}

class Order{
    String name;
    float price;

    public Order(String name, float price) {
        this.name = name;
        this.price = price;
    }
}

class User{
    int id;
    static int idSeed = 1;
    String email;
    String name;
    String phoneNumber;
    List<Order> orderList;

    public User(String email, String name, String phoneNumber) {
        this.id = idSeed++;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.orderList = new ArrayList<>();
    }

    public List<Order> getOrderList() {
        return orderList;
    }
}

class PizzaApp{
    Map<String,User> usersByEmail;
    Map<Order,Float> totalForEachPizza;

    public PizzaApp() {
        usersByEmail = new HashMap<>();
        totalForEachPizza = new HashMap<>();
    }


    public void registerUser(String email, String name, String phoneNumber) throws UserAlreadyExistsException {
        if(usersByEmail.containsKey(email))
            throw new UserAlreadyExistsException();
        usersByEmail.putIfAbsent(email, new User(email,name,phoneNumber));
    }

    public void makeOrder(String email, String pizzaName, float pizzaPrice) {
        usersByEmail.get(email).orderList.add(new Order(pizzaName,pizzaPrice));
    }

    public void printRevenueByPizza() {
        List<Order> allOrders = new ArrayList<>();
        usersByEmail.values().forEach(user -> allOrders.addAll(user.getOrderList()));
        allOrders.forEach(order -> {
            totalForEachPizza.computeIfPresent(order, (k,v) -> {
                v += order.price;
                return v;
            });
            totalForEachPizza.putIfAbsent(order, order.price);
        });
        totalForEachPizza.entrySet().stream()
                .sorted(Map.Entry.<Order, Float>comparingByValue()
                        .thenComparing((Comparator<? super Map.Entry<Order, Float>>) Map.Entry.comparingByKey()))
                .forEach(entry -> System.out.println(entry.getKey()));
    }

    public void printMostFrequentUserForPizza() {

    }
}

public class PizzaAppTestRe {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PizzaApp pizzaApp = new PizzaApp();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            String[] parts = line.split("\\s+");

            String method = parts[0];

            if(method.equalsIgnoreCase("registerUser")) { //email, name, phone
                String email = parts[1];
                String name = parts[2];
                String phone = parts[3];
                try {
                    pizzaApp.registerUser(email, name, phone);
                } catch (UserAlreadyExistsException e) {
                    System.out.println(e.getMessage());
                }
            } else if (method.equalsIgnoreCase("makeOrder")) { //email, pizzaName, price
                String email = parts[1];
                String pizzaName = parts[2];
                float price = Float.parseFloat(parts[3]);
                pizzaApp.makeOrder(email, pizzaName, price);
            } else if (method.equalsIgnoreCase("printRevenueByPizza")) {
                System.out.println("Print revenue by pizza");
                pizzaApp.printRevenueByPizza();
            } else { //printMostFrequentUserForPizza
                System.out.println("Print most frequent user(s) by pizza");

                pizzaApp.printMostFrequentUserForPizza();
            }
        }
    }
}