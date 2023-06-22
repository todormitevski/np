package ispitni_re.old_exams;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

class AmountNotAllowedException extends Exception{
    public AmountNotAllowedException(int amount) {
        super(String.format("Receipt with amount %d is not allowed to be scanned",amount));
    }
}

enum TaxType{
    A, //18%
    B, //5%
    V //0%
}

class Item{
    double price;
    TaxType taxType;

    public Item(int price, TaxType taxType) {
        this.price = price;
        this.taxType = taxType;
    }

    public double getPrice() {
        return price;
    }

    public String getTaxType() {
        return taxType.toString();
    }

    public double getDDV(){
        switch (taxType.toString()){
            case "A": return price * 0.18 * 0.15;
            case "B": return price * 0.05 * 0.15;
            default: return 0;
        }
    }
}

class Receipt{
    String id;
    List<Item> items = new ArrayList<>();

    public Receipt(String id, List<Item> items) {
        this.id = id;
        this.items = items;
    }

    public Receipt(String line) throws AmountNotAllowedException {
        String[] parts = line.split("\\s+");
        this.id = parts[0];

        int total = receiptAmountIsValid(parts);
        if(total > 30000)
            throw new AmountNotAllowedException(total);

        for(int i=1;i<parts.length-1;i+=2){
            items.add(new Item(Integer.parseInt(parts[i]),
                    TaxType.valueOf(parts[i+1])));
        }
    }

    public int receiptAmountIsValid(String[] articles){
        int total = 0;
        for(int i=1;i< articles.length-1;i+=2){
            total += Integer.parseInt(articles[i]);
        }
        return total;
    }

    public int getSumOfAmounts(){
        int total = 0;
        for(Item item : items){
            total += item.getPrice();
        }
        return total;
    }

    public double getTaxReturn(){
        return items.stream()
                .mapToDouble(Item::getDDV)
                .sum();
    }

    public String getId() {
        return id;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        return String.format("%s %d %.2f",id,getSumOfAmounts(),getTaxReturn());
    }
}

class MojDDV{
    List<Receipt> receipts;

    public MojDDV() {
        receipts = new ArrayList<>();
    }

    public void readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        receipts = br.lines()
                .map(line -> {
                    try {
                        return new Receipt(line);
                    } catch (AmountNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    void printTaxReturns (OutputStream outputStream){
        PrintWriter pw = new PrintWriter(outputStream);
        receipts.stream()
                .filter(Objects::nonNull)
                .forEach(pw::println);
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

    }
}
