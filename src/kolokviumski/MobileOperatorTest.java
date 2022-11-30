//package kolokviumski;
//
//import java.io.BufferedReader;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.sql.Array;
//import java.util.ArrayList;
//import java.util.DoubleSummaryStatistics;
//import java.util.List;
//import java.util.stream.Collectors;
//
//abstract class Customer{
//    String id;
//    double minutes;
//    int SMSs;
//    double GBs;
//
//    public Customer(String id, double minutes, int SMSs, double GBs) {
//        this.id = id;
//        this.minutes = minutes;
//        this.SMSs = SMSs;
//        this.GBs = GBs;
//    }
//
//    abstract double totalPrice();
//    abstract double commission();
//}
//
//class SCustomer extends Customer{
//
//    static double BASE_PRICE_S = 500.0;
//    static double FREE_MINUTES_S = 100.0;
//    static double FREE_SMS_S = 50;
//    static double FREE_GB_INTERNET_S = 5.0;
//
//    static double PRICE_PER_MINUTES = 5.0;
//    static double PRICE_PER_SMS = 6.0;
//    static double PRICE_PER_GB = 25.0;
//
//    static double COMMISSION_RATE = 0.07;
//
//    public SCustomer(String id, double minutes, int SMSs, double GBs) {
//        super(id, minutes, SMSs, GBs);
//    }
//
//    @Override
//    double totalPrice() {
//        double total = BASE_PRICE_S;
//        total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINUTES_S);
//        total += PRICE_PER_SMS * Math.max(0, SMSs - FREE_SMS_S);
//        total += PRICE_PER_GB * Math.max(0, GBs - FREE_GB_INTERNET_S);
//
////        if(minutes > FREE_MINUTES_S){
////            total += (PRICE_PER_MINUTES * (minutes - FREE_MINUTES_S));
////        }
////        if(SMSs > FREE_SMS_S){
////            total += (PRICE_PER_SMS * (SMSs - FREE_SMS_S));
////        }
////        if(GBs > FREE_GB_INTERNET_S){
////            total += (PRICE_PER_GB * (GBs - FREE_GB_INTERNET_S));
////        }
//        return total;
//    }
//
//    @Override
//    double commission() {
//        return totalPrice() * COMMISSION_RATE;
//    }
//}
//
//class MCustomer extends Customer{
//
//    static double BASE_PRICE_M = 500.0;
//    static double FREE_MINUTES_M = 100.0;
//    static double FREE_SMS_M = 50;
//    static double FREE_GB_INTERNET_M = 5.0;
//
//    static double PRICE_PER_MINUTES = 5.0;
//    static double PRICE_PER_SMS = 6.0;
//    static double PRICE_PER_GB = 25.0;
//
//    static double COMMISSION_RATE = 0.04;
//
//    public MCustomer(String id, double minutes, int SMSs, double GBs) {
//        super(id, minutes, SMSs, GBs);
//    }
//
//    @Override
//    double totalPrice() {
//        double total = BASE_PRICE_M;
//        total += PRICE_PER_MINUTES * Math.max(0, minutes - FREE_MINUTES_M);
//        total += PRICE_PER_SMS * Math.max(0, SMSs - FREE_SMS_M);
//        total += PRICE_PER_GB * Math.max(0, GBs - FREE_GB_INTERNET_M);
//
//        return total;
//    }
//
//    @Override
//    double commission() {
//        return totalPrice() * COMMISSION_RATE;
//    }
//}
//
//class SalesRep{
//    String id;
//    List<Customer> customers;
//
//    public SalesRep(String id, List<Customer> customers) {
//        this.id = id;
//        this.customers = customers;
//    }
//
//    public static SalesRep createSalesRep(String line){
//        //take line of input and convert to object
//        String [] parts = line.split("\\s+"); //regex: one or more space
//        String id = parts[0];
//        List<Customer> customers = new ArrayList<>();
//        for(int i=1;i<parts.length;i+=5){
//            String customerId = parts[i];
//            String type = parts[i+1];
//            double minutes = Double.parseDouble(parts[i+2]);
//            int sms = Integer.parseInt(parts[i+3]);
//            double gbs = Double.parseDouble(parts[i+4]);
//
//            if(type.equals("M")){
//                customers.add(new MCustomer(customerId, minutes, sms, gbs));
//            } else{
//                customers.add(new SCustomer(customerId, minutes, sms, gbs));
//            }
//        }
//        return new SalesRep(id, customers);
//    }
//
//    @Override
//    public String toString() {
//        DoubleSummaryStatistics summaryStatistics = customers.stream()
//                .mapToDouble(customers -> customers.totalPrice())
//                .summaryStatistics();
//        return String.format("%s Count: %d Min: %.2f Average: %.2f Max: %.2f Commission: %.2f",
//                id,
//                )
//    }
//}
//
//class MobileOperator{
//    List<SalesRep> salesReps;
//
//    void readSalesRepData(InputStream is) throws IOException {
//        BufferedReader br = new BufferedReader(new InputStreamReader(is));
//        br.lines().map(line -> SalesRep.createSalesRep(line)).collect(Collectors.toList());
//    }
//}
//
//public class MobileOperatorTest {
//    public static void main(String[] args) {
//        MobileOperator mobileOperator = new MobileOperator();
//        System.out.println("---- READING OF THE SALES REPORTS ----");
//        try {
//            mobileOperator.readSalesRepData(System.in);
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//        System.out.println("---- PRINTING FINAL REPORTS FOR SALES REPRESENTATIVES ----");
//        mobileOperator.printSalesReport(System.out);
//    }
//}
