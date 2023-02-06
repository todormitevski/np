package kolokviumski;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class InvalidIdException extends Exception{
    public InvalidIdException(String message) {
        super(message);
    }
}

abstract class Customer{
    String id;
    double minutes;
    int SMSs;
    double GBs;

    private static boolean isValidId(String id){
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

    public Customer(String id, double minutes, int SMSs, double GBs) throws InvalidIdException {
        if(!isValidId(id)){
            throw new InvalidIdException(String.format("%s is not a valid user ID",id));
        }
        this.id = id;
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
    static double FREE_GB_S = 5.0;

    static double PRICE_PER_MINUTE_S = 5.0;
    static double PRICE_PER_SMS_S = 6.0;
    static double PRICE_PER_GB_S = 25.0;

    static double COMMISSION_RATE_S = 0.07;

    public SCustomer(String id, double minutes, int SMSs, double GBs) throws InvalidIdException {
        super(id, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        double total = BASE_PRICE_S;
        if(minutes > FREE_MINUTES_S){
            total += (PRICE_PER_MINUTE_S * (minutes - FREE_MINUTES_S));
        }
        if(SMSs > FREE_SMS_S){
            total += (PRICE_PER_SMS_S * (SMSs - FREE_SMS_S));
        }
        if(GBs > FREE_GB_S){
            total += (PRICE_PER_GB_S * (GBs - FREE_GB_S));
        }
        return total;
    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE_S;
    }
}

class MCustomer extends Customer{
    static double BASE_PRICE_M = 750.0;
    static double FREE_MINUTES_M = 150.0;
    static int FREE_SMS_M = 60;
    static double FREE_GB_M = 10.0;

    static double PRICE_PER_MINUTE_M = 4.0;
    static double PRICE_PER_SMS_M = 4.0;
    static double PRICE_PER_GB_M = 20.0;

    static double COMMISSION_RATE_M = 0.04;

    public MCustomer(String id, double minutes, int SMSs, double GBs) throws InvalidIdException {
        super(id, minutes, SMSs, GBs);
    }

    @Override
    double totalPrice() {
        double total = BASE_PRICE_M;
        if(minutes > FREE_MINUTES_M){
            total += (PRICE_PER_MINUTE_M * (minutes - FREE_MINUTES_M));
        }
        if(SMSs > FREE_SMS_M){
            total += (PRICE_PER_SMS_M * (SMSs - FREE_SMS_M));
        }
        if(GBs > FREE_GB_M){
            total += (PRICE_PER_GB_M * (GBs - FREE_GB_M));
        }
        return total;
    }

    @Override
    double commission() {
        return totalPrice() * COMMISSION_RATE_M;
    }
}

class SalesRep implements Comparable<SalesRep>{
    String id;
    List<Customer> customers;

    public SalesRep(String id, List<Customer> customers) {
        this.id = id;
        this.customers = customers;
    }

    private static boolean isValidId(String id) throws InvalidIdException {
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

    public static SalesRep createSalesRep(String line) throws InvalidIdException {
        String[] parts = line.split("\\s+");
        List<Customer> customers = new ArrayList<>();
        String idRep = parts[0];

        if(!isValidId(idRep)){
            throw new InvalidIdException(String.format("%s is not a valid sales rep ID",idRep));
        }

        for(int i=1;i<parts.length;i+=5){
            String idCustomer = parts[i];
            String packetType = parts[i+1];
            double mins = Double.parseDouble(parts[i+2]);
            int smss = Integer.parseInt(parts[i+3]);
            double gbs = Double.parseDouble(parts[i+4]);

            try {
                if (packetType.equals("S")) {
                    SCustomer customer = new SCustomer(idCustomer, mins, smss, gbs);
                    customers.add(customer);
                } else {
                    MCustomer customer = new MCustomer(idCustomer, mins, smss, gbs);
                    customers.add(customer);
                }
            } catch(InvalidIdException e){
                System.out.println(e.getMessage());
            }
        }
        return new SalesRep(idRep,customers);
    }

    @Override
    public String toString() {
        DoubleSummaryStatistics summaryStatistics = customers.stream()
                .mapToDouble(customer -> customer.totalPrice())
                .summaryStatistics();
        return String.format(
                "%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
                id,
                summaryStatistics.getCount(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax(),
                totalCommission()
        );
    }

    private double totalCommission(){
        return customers.stream().mapToDouble(Customer::commission).sum();
    }

    @Override
    public int compareTo(SalesRep o) {
        return Double.compare(this.totalCommission(),o.totalCommission());
    }
}

class MobileOperator{
    List<SalesRep> salesReps;

    public MobileOperator() {
        salesReps = new ArrayList<>();
    }

    void readSalesRepData(InputStream is){
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        salesReps = br.lines()
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
    }

    void printSalesReport(OutputStream os){
        PrintWriter printWriter = new PrintWriter(os);
        salesReps.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(printWriter::println);
        printWriter.flush();
    }
}

public class MobileOperatorTest1 {
    public static void main(String[] args) {
        MobileOperator mobileOperator = new MobileOperator();
        System.out.println("---- READING OF THE SALES REPORTS ----");
        mobileOperator.readSalesRepData(System.in);
        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
        mobileOperator.printSalesReport(System.out);
    }
}
