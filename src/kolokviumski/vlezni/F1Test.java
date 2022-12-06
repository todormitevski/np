package kolokviumski.vlezni;

import java.io.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

class Lap implements Comparable<Lap>{
    int min;
    int sec;
    int millisec;

    public Lap(int min, int sec, int millisec) {
        this.min = min;
        this.sec = sec;
        this.millisec = millisec;
    }

    public int calcTime(){
        return millisec + sec * 1000 + min * 1000 * 60;
    }

    @Override
    public int compareTo(Lap o) {
        return Integer.compare(this.calcTime(), o.calcTime());
    }

    @Override
    public String toString() {
        return String.format("%d:%02d:%03d",
                min,sec,millisec);
    }
}

class DriverFactory{
    private static Lap createLap(String line){
        String[] parts = line.split(":");
        int min = Integer.parseInt(parts[0]);
        int sec = Integer.parseInt(parts[1]);
        int millisec = Integer.parseInt(parts[2]);
        return new Lap(min,sec,millisec);
    }

    public static Driver createDriver(String line){
        String[] parts = line.split("\\s+");
        String name = parts[0];
        Lap lap1 = createLap(parts[1]);
        Lap lap2 = createLap(parts[2]);
        Lap lap3 = createLap(parts[3]);
        return new Driver(name,lap1,lap2,lap3);
    }
}

class Driver implements Comparable<Driver>{
    String name;
    Lap lap1;
    Lap lap2;
    Lap lap3;

    public Driver(String name, Lap lap1, Lap lap2, Lap lap3) {
        this.name = name;
        this.lap1 = lap1;
        this.lap2 = lap2;
        this.lap3 = lap3;
    }

    public Lap findFastestLap(){
        int lap1Time = lap1.calcTime();
        int lap2Time = lap2.calcTime();
        int lap3Time = lap3.calcTime();

        int min = Math.min(lap3Time, Math.min(lap1Time,lap2Time));
        if(min == lap1Time)
            return lap1;
        if(min == lap2Time)
            return lap2;
        else return lap3;
    }

    @Override
    public int compareTo(Driver o) {
        return this.findFastestLap().compareTo(o.findFastestLap());
    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, findFastestLap());
    }
}

public class F1Test {
    List<Driver> drivers;

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}

class F1Race {
    List<Driver> drivers;

    public F1Race(List<Driver> drivers) {
        this.drivers = drivers;
    }

    public F1Race() {
    }

    public void readResults(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        this.drivers = br.lines()
                .map(DriverFactory::createDriver)
                .collect(Collectors.toList());
    }


    public void printSorted(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        drivers = drivers.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        for (int i=0;i<drivers.size();i++) {
            pw.print(i+1 + ". ");
            pw.println(drivers.get(i));
        }
        pw.flush();
    }
}