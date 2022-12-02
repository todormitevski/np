package kolokviumski;

import java.io.*;
import java.lang.reflect.AnnotatedArrayType;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

enum TaxType{
    A,
    B,
    V
}

class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(long sum) {
        super(String.format("Receipt with amount %d is not allowed to be scanned", sum));
    }
}

class Item{
    int price;
    TaxType type;

    public Item(int price, TaxType type) {
        this.price = price;
        this.type = type;
    }

    public Item() {
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setType(TaxType type) {
        this.type = type;
    }

    public double getTaxReturn(){
        switch(type){
            case A:
                return price * 0.18 * 0.15;
            case B:
                return price * 0.05 * 0.15;
            default:
                return 0;
        }
    }
}

class Receipt{
    String id;
    List<Item> items;

    public Receipt(String id, List<Item> items) throws AmountNotAllowedException {
        this.id = id;
        int sum = items.stream().mapToInt(item -> item.price).sum();
        if(sum > 30000)
            throw new AmountNotAllowedException(sum);
        this.items = items;
    }

    public static Receipt createReceipt(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        List<Item> items = new ArrayList<>();
        Item item = new Item();

        for(int i=1;i<parts.length;i++){
            if(i % 2 == 0){
                item.setType(TaxType.valueOf(parts[i]));
                items.add(item);
                item = new Item();
            } else{
                item.setPrice(Integer.parseInt(parts[i]));
            }
        }
        return new Receipt(id, items);
    }

    public double getTaxReturn(){
        return items.stream()
                .mapToDouble(Item::getTaxReturn)
                .sum();
    }

    public String getId() {
        return id;
    }

    public int getSum(){
        return items.stream()
                .mapToInt(item -> item.price).sum();
    }

    @Override
    public String toString() {
        return String.format("%10s\t%10d\t%10.5f",
                this.id, this.getSum(), this.getTaxReturn());
    }
}

class MojDDV{
    List<Receipt> receipts;

    public MojDDV(List<Receipt> receipts) {
        this.receipts = receipts;
    }

    public MojDDV() {
        this.receipts = new ArrayList<>();
    }

    public void readRecords(InputStream in){
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
        receipts = bufferedReader.lines()
                .map(line -> {
                    try {
                        return Receipt.createReceipt(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    public void printTaxReturns(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);
        receipts.forEach(out::println);
        pw.flush();
    }

    void printStatistics(OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        DoubleSummaryStatistics summaryStatistics = receipts.stream()
                .mapToDouble(Receipt::getTaxReturn).summaryStatistics();

        pw.println(String.format("min:\t%05.03f\nmax:\t%05.03f" +
                        "\nsum:\t%05.03f\ncount:\t%-5d\navg:\t%05.03f",
                summaryStatistics.getMin(),
                summaryStatistics.getMax(),
                summaryStatistics.getSum(),
                summaryStatistics.getCount(),
                summaryStatistics.getAverage()));
        pw.flush();
    }
}

public class MojDDVTest {

    public static void main(String[] args) {

        MojDDV mojDDV = new MojDDV();

        System.out.println("===READING RECORDS FROM INPUT STREAM===");
        mojDDV.readRecords(System.in);

        System.out.println("===PRINTING TAX RETURNS RECORDS TO OUTPUT STREAM ===");
        mojDDV.printTaxReturns(System.out);

        System.out.println("===PRINTING SUMMARY STATISTICS FOR TAX RETURNS TO OUTPUT STREAM===");
        mojDDV.printStatistics(System.out);

    }
}
