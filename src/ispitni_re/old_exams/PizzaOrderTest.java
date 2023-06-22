//package ispitni_re.old_exams;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Scanner;
// //finish later
//class ItemOutOfStockException extends Exception{
//    public ItemOutOfStockException(Item item) {
//        super(String.format("Item %s out of stock!", item));
//    }
//}
//
//class InvalidExtraTypeException extends Exception{
//    public InvalidExtraTypeException() {
//        super("InvalidExtraTypeException");
//    }
//}
//
//class InvalidPizzaTypeException extends Exception{
//    public InvalidPizzaTypeException() {
//        super("InvalidPizzaTypeException");
//    }
//}
//
//interface Item{
//    public abstract int getPrice();
//}
//
//class ExtraItem implements Item{
//    String type;
//    List<String> validValues = List.of("Coke","Ketchup");
//
//    public ExtraItem(String type) throws InvalidExtraTypeException {
//        if(!this.validValues.contains(type))
//            throw new InvalidExtraTypeException();
//        this.type = type;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    @Override
//    public int getPrice() {
//        if(type.equals("Coke"))
//            return 5;
//        else return 3;
//    }
//}
//
//class PizzaItem implements Item{
//    String type;
//    List<String> validValues = List.of("Standard","Pepperoni","Vegetarian");
//
//    public PizzaItem(String type) throws InvalidPizzaTypeException {
//        if(!this.validValues.contains(type))
//            throw new InvalidPizzaTypeException();
//        this.type = type;
//    }
//
//    public String getType() {
//        return type;
//    }
//
//    @Override
//    public int getPrice() {
//        switch (type){ //enhanced switch doesn't work on courses
//            case "Standard": return 10;
//            case "Pepperoni": return 12;
//            default: return 8; //Vegetarian
//        }
//    }
//}
//
//class OrderedItem{
//    Item item;
//    int count;
//
//    public OrderedItem(Item item, int count) {
//        this.item = item;
//        this.count = count;
//    }
//
//    public Item getItem() {
//        return item;
//    }
//
//    public int getCount() {
//        return count;
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "%s15 x %d%5d$",
//                item.
//        );
//    }
//}
//
//class Order{
//    List<OrderedItem> items;
//
//    public Order() {
//        this.items = new ArrayList<>();
//    }
//
//    public void addItem(Item item, int count) throws ItemOutOfStockException {
//        if(count > 10)
//            throw new ItemOutOfStockException(item);
//        OrderedItem oi = new OrderedItem(item, count);
//        int index = items.indexOf(oi);
//        if(index != -1) //if it exists
//            items.set(index, oi); //replace it
//        else
//            items.add(oi);
//    }
//
//    public int getPrice(){
//        int total = 0;
//        for(OrderedItem oi : items){
//            total += oi.getItem().getPrice() * oi.getCount();
//        }
//        return total;
//    }
//
//    public void displayOrder(){
//
//    }
//}
//
//public class PizzaOrderTest {
//
//    public static void main(String[] args) {
//        Scanner jin = new Scanner(System.in);
//        int k = jin.nextInt();
//        if (k == 0) { //test Item
//            try {
//                String type = jin.next();
//                String name = jin.next();
//                Item item = null;
//                if (type.equals("Pizza")) item = new PizzaItem(name);
//                else item = new ExtraItem(name);
//                System.out.println(item.getPrice());
//            } catch (Exception e) {
//                System.out.println(e.getClass().getSimpleName());
//            }
//        }
//        if (k == 1) { // test simple order
//            Order order = new Order();
//            while (true) {
//                try {
//                    String type = jin.next();
//                    String name = jin.next();
//                    Item item = null;
//                    if (type.equals("Pizza")) item = new PizzaItem(name);
//                    else item = new ExtraItem(name);
//                    if (!jin.hasNextInt()) break;
//                    order.addItem(item, jin.nextInt());
//                } catch (Exception e) {
//                    System.out.println(e.getClass().getSimpleName());
//                }
//            }
//            jin.next();
//            System.out.println(order.getPrice());
//            order.displayOrder();
//            while (true) {
//                try {
//                    String type = jin.next();
//                    String name = jin.next();
//                    Item item = null;
//                    if (type.equals("Pizza")) item = new PizzaItem(name);
//                    else item = new ExtraItem(name);
//                    if (!jin.hasNextInt()) break;
//                    order.addItem(item, jin.nextInt());
//                } catch (Exception e) {
//                    System.out.println(e.getClass().getSimpleName());
//                }
//            }
//            System.out.println(order.getPrice());
//            order.displayOrder();
//        }
//        if (k == 2) { // test order with removing
//            Order order = new Order();
//            while (true) {
//                try {
//                    String type = jin.next();
//                    String name = jin.next();
//                    Item item = null;
//                    if (type.equals("Pizza")) item = new PizzaItem(name);
//                    else item = new ExtraItem(name);
//                    if (!jin.hasNextInt()) break;
//                    order.addItem(item, jin.nextInt());
//                } catch (Exception e) {
//                    System.out.println(e.getClass().getSimpleName());
//                }
//            }
//            jin.next();
//            System.out.println(order.getPrice());
//            order.displayOrder();
//            while (jin.hasNextInt()) {
//                try {
//                    int idx = jin.nextInt();
//                    order.removeItem(idx);
//                } catch (Exception e) {
//                    System.out.println(e.getClass().getSimpleName());
//                }
//            }
//            System.out.println(order.getPrice());
//            order.displayOrder();
//        }
//        if (k == 3) { //test locking & exceptions
//            Order order = new Order();
//            try {
//                order.lock();
//            } catch (Exception e) {
//                System.out.println(e.getClass().getSimpleName());
//            }
//            try {
//                order.addItem(new ExtraItem("Coke"), 1);
//            } catch (Exception e) {
//                System.out.println(e.getClass().getSimpleName());
//            }
//            try {
//                order.lock();
//            } catch (Exception e) {
//                System.out.println(e.getClass().getSimpleName());
//            }
//            try {
//                order.removeItem(0);
//            } catch (Exception e) {
//                System.out.println(e.getClass().getSimpleName());
//            }
//        }
//    }
//
//}
