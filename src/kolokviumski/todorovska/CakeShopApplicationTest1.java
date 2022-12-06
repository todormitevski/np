package kolokviumski.todorovska;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Item{
    String itemName;
    int itemPrice;

    public Item(String itemName, int itemPrice) {
        this.itemName = itemName;
        this.itemPrice = 0;
    }

    public Item(String itemName) {
        this.itemName = itemName;
    }

    public Item() {
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(int itemPrice) {
        this.itemPrice = itemPrice;
    }

    @Override
    public String toString() {
        return "Item{" +
                "itemName='" + itemName + '\'' +
                ", itemPrice='" + itemPrice + '\'' +
                '}';
    }
}

class Order implements Comparable<Order>{
    private int id;
    private List<Item> items;

    public Order() {
        id = -1;
        items = new ArrayList<>();
    }

    public Order(int id) {
        this.id = id;
    }

    public static Order createOrder(String line){
        String[] parts = line.split("\\s+");
        int orderId = Integer.parseInt(parts[0]);
        List<Item> items = new ArrayList<>();

        Arrays.stream(parts)
                .skip(1)
                .forEach(part ->{
                    if(Character.isAlphabetic(part.charAt(0)))
                        items.add(new Item());
                    else
                        items.get(items.size() - 1).setItemPrice(Integer.parseInt(part));
                });
        return new Order(orderId);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return id + " " + items.size();
    }

    @Override
    public int compareTo(Order o) {
        return Integer.compare(this.items.size(), o.items.size());
    }
}

class CakeShopApplication{
    private List<Order> orders;

    public CakeShopApplication() {
        orders = new ArrayList<>();
    }

    public int readCakeOrders(InputStream inputStream) {
        orders = new BufferedReader(new InputStreamReader(inputStream))
                .lines()
                .map(Order::createOrder)
                .collect(Collectors.toList());
        return orders.stream()
                .mapToInt(order -> order.getItems().size())
                .sum();
    }

    public void printLongestOrder(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        Order longestOrder = orders.stream()
                .max(Comparator.naturalOrder())
                .orElseGet(Order::new);
        pw.write(longestOrder.toString());
        pw.flush();
    }
}

public class CakeShopApplicationTest1 {
    public static void main(String[] args) {
        CakeShopApplication cakeShopApplication = new CakeShopApplication();

        System.out.println("--- READING FROM INPUT STREAM ---");
        System.out.println(cakeShopApplication.readCakeOrders(System.in));

        System.out.println("--- PRINTING LARGEST ORDER TO OUTPUT STREAM ---");
        cakeShopApplication.printLongestOrder(System.out);
    }
}
