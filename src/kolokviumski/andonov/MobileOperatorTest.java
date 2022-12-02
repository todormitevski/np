package kolokviumski.andonov;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIdException extends Exception{
    public InvalidIdException(String message) {
        super(message);
    }
}

abstract class Customer{
    String customerId;
    double minutes;
    int SMSs;
    double GBs;

    private static boolean isIdValid(String id){
        if(id.length() != 7){
            return false;
        }
        for (int i=0;i<7;i++){
            if(!Character.isDigit(id.charAt(i)))
                return false;
        }
        return true;
    }

    public Customer(String customerId, double minutes, int SMSs, double GBs) throws InvalidIdException {
        if(!isIdValid(customerId))
            throw new InvalidIdException(String.format("%s is not a valid user ID",customerId));
        this.customerId = customerId;
        this.minutes = minutes;
        this.SMSs = SMSs;
        this.GBs = GBs;
    }

    abstract double totalPrice();
    abstract double commission();
}

class SCustomer extends Customer{
    static double BASE_PRICE_S = 500.0;
    static double FREE_MINUTES_S = 100.0;
    static int FREE_SMS_S = 50;
    static double FREE_GB_INTERNET_S = 5.0;

    static double PRICE_PER_MINUTES = 5.0;
    static double PRICE_PER_SMS = 6.0;
    static double PRICE_PER_GB = 25.0;

    static double COMMISSION_RATE_S = 0.07;

    public SCustomer(String customerId, double minutes, int SMSs, double GBs) throws InvalidIdException {
        super(customerId, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        double total = BASE_PRICE_S;

        //another way to do it
//        total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINUTES_S);

        if(minutes > FREE_MINUTES_S)
            total += (PRICE_PER_MINUTES * (minutes - FREE_MINUTES_S));
        if(SMSs > FREE_SMS_S)
            total += (PRICE_PER_SMS * (SMSs - FREE_SMS_S));
        if(GBs > FREE_GB_INTERNET_S)
            total += (PRICE_PER_GB * (GBs - FREE_GB_INTERNET_S));

        return total;
    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE_S;
    }
}

class MCustomer extends Customer{

    static double BASE_PRICE_M = 500.0;
    static double FREE_MINUTES_M = 100.0;
    static int FREE_SMS_M = 50;
    static double FREE_GB_INTERNET_M = 5.0;

    static double PRICE_PER_MINUTES = 5.0;
    static double PRICE_PER_SMS = 6.0;
    static double PRICE_PER_GB = 25.0;

    static double COMMISSION_RATE_M = 0.04;

    public MCustomer(String customerId, double minutes, int SMSs, double GBs) throws InvalidIdException {
        super(customerId, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        double total = BASE_PRICE_M;

        if(minutes > FREE_MINUTES_M)
            total += (PRICE_PER_MINUTES * (minutes - FREE_MINUTES_M));
        if(SMSs > FREE_SMS_M)
            total += (PRICE_PER_SMS * (SMSs - FREE_SMS_M));
        if(GBs > FREE_GB_INTERNET_M)
            total += (PRICE_PER_GB * (GBs - FREE_GB_INTERNET_M));

        return total;
    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE_M;
    }
}

class SalesRep implements Comparable<SalesRep> {
    String id;
    List<Customer> customers;

    public SalesRep(String id, List<Customer> customers) {
        this.id = id;
        this.customers = customers;
    }

    private static boolean isIdValid(String id) throws InvalidIdException {
        if(id.length() != 3)
            return false;
        for(int i=0;i<3;i++){
            if(Character.isDigit(id.charAt(i)))
                return false;
        }
        return true;
    }

    public static SalesRep createSalesRep(String line) throws InvalidIdException {
        String[] parts = line.split("\\s+");
        String id = parts[0];
        if(!isIdValid(id)){
            throw new InvalidIdException(String.format("%s is not a valid sales rep ID", id));
        }
        List<Customer> customers = new ArrayList<>();
        for(int i=1;i<parts.length;i+=5){
            String customerId = parts[i];
            String type = parts[i+1];
            double minutes = Double.parseDouble(parts[i+2]);
            int sms = Integer.parseInt(parts[i+3]);
            double gbs = Double.parseDouble(parts[i+4]);

            if(type.equals("M"))
                customers.add(new MCustomer(customerId, minutes, sms, gbs));
            else customers.add(new SCustomer(customerId, minutes, sms, gbs));
        }
        return new SalesRep(id, customers);
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics summaryStatistics = customers.stream()
                .mapToDouble(customer -> customer.totalPrice())
                .summaryStatistics();
        return String.format("%s Count: %d" +
                        "Min: %.2f Average %.2f" +
                        "Max: %.2f Commission: %.2f",
                summaryStatistics.getCount(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax(),
                totalCommission());
    }

    private double totalCommission(){
        return customers.stream()
                .mapToDouble(Customer::commission)
                .sum();
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.totalCommission(), o.totalCommission());
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
                    } catch (InvalidIdException e) {
                        System.out.println(e.getMessage());
                        return null;
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        br.close();
    }

    public void printSalesReport(PrintStream out) {
        PrintWriter pw = new PrintWriter(out);

        salesReps.stream()
                        .sorted(Comparator
                                .reverseOrder())
                                .forEach(pw::println);

        pw.println();
        pw.flush();
    }
}

public class MobileOperatorTest {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---READING OF THE SALES REPORTS---");
        try {
            mobileOperator.readSalesRepData(System.in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("---PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES");
        mobileOperator.printSalesReport(System.out);
    }
}
