package kolokviumski.andonov;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

class InvalidPackageException extends Exception {
    public InvalidPackageException() {
    }

    public InvalidPackageException(String message) {
        super(message);
    }
}

abstract class Package implements Comparable<Package> {
    String name;
    String address;
    int trackingNumber;
    int weight;

    public Package(String name, String address, int trackingNumber, int weight) {
        this.name = name;
        this.address = address;
        this.trackingNumber = trackingNumber;
        this.weight = weight;
    }

    abstract double price();

    @Override
    public String toString() {
        return String.format("%s, %s, %d, %d, ",
                name,address,trackingNumber,weight);
    }

    @Override
    public int compareTo(Package o){
        return Double.compare(this.price(), o.price());
    }
}

class PackageFactory{
    public static Package createPackage(String line) throws InvalidPackageException {
        String[] parts = line.split("\\s+");
        String type = parts[0];
        String name = parts[1];
        String address = parts[2];
        int trackingNumber = Integer.parseInt(parts[3]);
        int weight = Integer.parseInt(parts[4]);

        if(weight <= 0)
            throw new InvalidPackageException(line);

        if(type.equals("I")) { //international
            String region = parts[5];
            return new InternationalPackage(name,address,trackingNumber,weight,region);
        } else if(type.equals("L")){ //local
            boolean prio = Boolean.parseBoolean(parts[5]);
            return new LocalPackage(name,address,trackingNumber,weight,prio);
        } else{
            throw new InvalidPackageException(line);
        }
    }
}

class InternationalPackage extends Package{
    String region;

    final static double PRICE_PER_GRAM = 1.5;

    public InternationalPackage(String name, String address, int trackingNumber, int weight, String region) {
        super(name, address, trackingNumber, weight);
        this.region = region;
    }

    @Override
    double price() {
        return weight * PRICE_PER_GRAM;
    }

    @Override
    public String toString() {
        return String.format("I, %s, %s", super.toString(), region);
    }
}

class LocalPackage extends Package{
    boolean priority;

    final static double PRICE_WITH_PRIO = 5.0;
    final static double PRICE_WITHOUT_PRIO = 3.0;

    public LocalPackage(String name, String address, int trackingNumber, int weight, boolean priority) {
        super(name, address, trackingNumber, weight);
        this.priority = priority;
    }

    @Override
    double price() {
        if(priority)
            return PRICE_WITH_PRIO;
        else return PRICE_WITHOUT_PRIO;
    }

    @Override
    public String toString() {
        return String.format("L, %s, %s", super.toString(), priority);
    }
}

class PostOffice{
    String namePO;
    String location;
    List<Package> packages;

    public PostOffice(String namePO, String location) {
        this.namePO = namePO;
        this.location = location;
        packages = new ArrayList<>();
    }


    public void loadPackages(Scanner sc) throws InvalidPackageException {
        //couldn't do this way because of exception error
//        BufferedReader br = new BufferedReader(new InputStreamReader(in));
//        packages = br.lines() //creates stream of strings
//                .map(PackageFactory::createPackage)
//                .collect(Collectors.toList());
        //not allowed to propagate in lambda (has to be surrounded by try/catch)

        ///Scanner sc = new Scanner(in);

        while(sc.hasNextLine()){
            String line = sc.nextLine();
            packages.add(PackageFactory.createPackage(line));
        }
    }

    public boolean addPackage(Package p){
        return packages.add(p);
    }

    public void printPackages(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        packages.stream()
                .sorted(Comparator.reverseOrder())
                .forEach(p -> pw.println());
        pw.flush();
    }

    public Package mostExpensive() {
        //w/o streams
//        Package max = packages.get(0);
//        for(Package p: packages){
//            if(p.compareTo(max) > 0)
//                max = p;
//        }
//        return max;

        return packages.stream()
                .max(Comparator.naturalOrder())
                .get();
    }
}

public class PostOfficeTester {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        PostOffice office = new PostOffice("Poshta", "Skopje");
        try{
            office.loadPackages(sc);
            System.out.println("===PACKAGES===");
            office.printPackages(System.out);
            System.out.println();
            System.out.println("===MostExpensive===");
            System.out.println(office.mostExpensive());
        } catch (Exception e){
            System.out.println("===TestingException===");
            System.out.println("Invalid value:");
            System.out.println(e.getMessage());
        }
        sc.close();
    }
}
