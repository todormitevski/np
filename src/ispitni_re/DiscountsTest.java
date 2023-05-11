package ispitni_re;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Price{
    int discounted;
    int standard;

    public Price(int discounted, int standard) {
        this.discounted = discounted;
        this.standard = standard;
    }

    public int getStandard() {
        return standard;
    }

    public double calcDiscountPercent(){
        return Math.floor((standard - discounted) / (double) standard * 100);
    }

    public int calcDiscount(){
        return standard - discounted;
    }

    @Override
    public String toString() {
        return String.format("%2.0f%% %d/%d", calcDiscountPercent(), discounted, standard);
    }
}

class Store{
    String name;
    List<Price> prices;

    public Store(String name, List<Price> prices) {
        this.name = name;
        this.prices = prices;
    }

    public double calcAverageDiscount(){
        double avg = 0.0;
        for(Price price : prices){
            avg += price.calcDiscountPercent();
        }
        return avg / prices.size();
    }

    public int calcTotalDiscount(){
        return prices.stream()
                .mapToInt(Price::calcDiscount)
                .sum();
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(name + "\n");
        sb.append(String.format("Average discount: %.1f%%\n",calcAverageDiscount()));
        sb.append(String.format("Total discount: %d\n",calcTotalDiscount()));
        prices.sort(Comparator.comparing(Price::calcDiscountPercent)
                .thenComparing(Price::getStandard).reversed());
        for(Price price : prices){
            sb.append(price.toString()).append("\n");
        }
        return sb.substring(0,sb.length()-1);
    }
}

class Discounts{
    List<Store> stores;

    public Discounts() {
        stores = new ArrayList<Store>();
    }

    public int readStores(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("\\s+");
                    List<Price> prices = new ArrayList<Price>();
                    Arrays.stream(parts)
                            .skip(1)
                            .forEach(part ->{
                                String[] priceParts = part.split(":");
                                prices.add(new Price(
                                        Integer.parseInt(priceParts[0]),
                                        Integer.parseInt(priceParts[1])
                                ));
                            });
                    stores.add(new Store(parts[0],prices));
                });
        return stores.size();
    }

    public List<Store> byAverageDiscount() {
        return stores.stream()
                .sorted(Comparator.comparing(Store::calcAverageDiscount)
                        .thenComparing(Store::getName).reversed())
                .limit(3)
                .collect(Collectors.toList());
    }

    public List<Store> byTotalDiscount() {
        return stores.stream()
                .sorted(Comparator.comparing(Store::calcTotalDiscount)
                        .thenComparing(Store::getName))
                .limit(3)
                .collect(Collectors.toList());
    }
}

/**
 * Discounts
 */
public class DiscountsTest {
    public static void main(String[] args) {
        Discounts discounts = new Discounts();
        int stores = discounts.readStores(System.in);
        System.out.println("Stores read: " + stores);
        System.out.println("=== By average discount ===");
        discounts.byAverageDiscount().forEach(System.out::println);
        System.out.println("=== By total discount ===");
        discounts.byTotalDiscount().forEach(System.out::println);
    }
}

// Vashiot kod ovde
