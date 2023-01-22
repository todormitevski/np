package kolokviumski.rerun;

import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidException extends Exception{
    public InvalidException(String message){
        super(message);
    }
}

abstract class Customer{
    String id;
    double min;
    int sms;
    double net;

    private static boolean isValid(String id){
        if(id.length() != 7){
            return false;
        }
        for(int i=0;i<7;i++){
            if(!Character.isDigit(id.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public Customer(String id, double min, int sms, double net) throws InvalidException {
        if(!isValid(id)){
            throw new InvalidException(String.format("%s is not a valid sales rep ID", id));
        }
        this.id = id;
        this.min = min;
        this.sms = sms;
        this.net = net;
    }

    abstract double price();
    abstract double provision();
}

class SCustomer extends Customer{
    static double BASE_PRICE = 500.0;
    static double FREE_MINUTES_S = 100.0;
    static double FREE_SMS_S = 50;
    static double FREE_GB_NET_S = 5.0;

    static double S_PRICE_PER_MINUTE = 5.0;
    static double S_PRICE_PER_SMS = 6.0;
    static double S_PRICE_PER_GB = 25.0;

    static double PROVISION_RATE = 0.07;

    public SCustomer(String id, double min, int sms, double net) throws InvalidException {
        super(id, min, sms, net);
    }

    @Override
    double price() {
        double total = BASE_PRICE;
        if(min > FREE_MINUTES_S)
            total += (min - FREE_MINUTES_S) * S_PRICE_PER_MINUTE;
        if(sms > FREE_SMS_S)
            total += (sms - FREE_SMS_S) * S_PRICE_PER_SMS;
        if(net > FREE_GB_NET_S)
            total += (net - FREE_GB_NET_S) * S_PRICE_PER_GB;
        return total;
    }

    @Override
    double provision() {
        return price() * PROVISION_RATE;
    }
}

class MCustomer extends Customer{
    static double BASE_PRICE = 750.0;
    static double FREE_MINUTES_M = 100.0;
    static double FREE_SMS_M = 50;
    static double FREE_GB_NET_M = 5.0;

    static double M_PRICE_PER_MINUTE = 4.0;
    static double M_PRICE_PER_SMS = 4.0;
    static double M_PRICE_PER_GB = 20.0;

    static double PROVISION_RATE = 0.04;

    public MCustomer(String id, double min, int sms, double net) throws InvalidException {
        super(id, min, sms, net);
    }

    @Override
    double price() {
        double total = BASE_PRICE;
        total += Math.max(0,min-FREE_MINUTES_M) * M_PRICE_PER_MINUTE;
        total += Math.max(0,sms-FREE_SMS_M) * M_PRICE_PER_SMS;
        total += Math.max(0,net-FREE_GB_NET_M) * M_PRICE_PER_GB;
        return total;
    }

    @Override
    double provision() {
        return price() * PROVISION_RATE;
    }
}

class SalesRep implements Comparable<SalesRep>{
    String id;
    List<Customer> customers;

    public SalesRep(String id, List<Customer> customers) {
        this.id = id;
        this.customers = customers;
    }

    private static boolean isIdValid(String id){
        if(id.length() != 3){
            return false;
        }
        for(int i=0;i<3;i++){
            if(!Character.isDigit(id.charAt(i))){
                return false;
            }
        }
        return true;
    }

    public static SalesRep createSalesRep(String line) throws InvalidException {
        String [] parts = line.split("\\s+");
        String id = parts[0];

        if (!isIdValid(id)) throw new InvalidException(String.format("%s is not a valid sales rep ID", id));

        List<Customer> customers = new ArrayList<>();
        for(int i=1;i<parts.length;i+=5){
            String customerID = parts[i];
            String type = parts[i+1];
            double min = Double.parseDouble(parts[i+2]);
            int sms = Integer.parseInt(parts[i+3]);
            double net = Double.parseDouble(parts[i+4]);

            try{
                if (type.equals("M")){
                    customers.add(new MCustomer(
                            customerID,min,
                            sms,net
                            ));
                }
                else{
                    customers.add(new SCustomer(
                            customerID,min,
                            sms,net
                    ));
                }
            }
            catch(InvalidException e){
                e.printStackTrace();
            }
        }
        return new SalesRep(id,customers);
    }

    private double totalProvision(){
        return customers.stream()
                .mapToDouble(Customer::provision).sum();
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics summaryStatistics =
                customers.stream()
                        .mapToDouble(customer -> customer.price())
                        .summaryStatistics();

        return String.format("%s Count: %d Min: %2f " +
                "Average: %2f Max: %2f " +
                "Commission: %2f",
                id,
                summaryStatistics.getCount(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax(),
                totalProvision()
        );
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.totalProvision(), o.totalProvision());
    }
}

class MobileOperator{
    List<SalesRep> salesReps;
    public void readSalesRepData(InputStream in) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        br.lines()
                .map(line -> {
                    try {
                        return SalesRep.createSalesRep(line);
                    } catch (InvalidException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        br.close();
    }

    public void printSalesReport(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);

        salesReps.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(salesRep -> pw.println());
        pw.flush();
    }
}

public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        try {
            mobileOperator.readSalesRepData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}
