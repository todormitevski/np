package ispitni_re.old_exams;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Driver{
    String name;
    List<Integer> laps;
    int bestTime;

    public Driver(String name) {
        this.name = name;
        laps = new ArrayList<>();
        bestTime = 0;
    }

    public void addLap(String lap){
        String[] parts = lap.split(":");
        int timeMs = Integer.parseInt(parts[2])
                + Integer.parseInt(parts[1]) * 1000
                + Integer.parseInt(parts[0]) * 60 * 1000;
        laps.add(timeMs);
    }

    public void calcBestTime(){
        this.bestTime = laps.stream().min(Comparator.naturalOrder()).get();
    }

    public String getName() {
        return name;
    }

    public int getBestTime() {
        return bestTime;
    }

    public String timeToString(){
        int min = (bestTime / 1000) / 60;
        int sec = (bestTime - min * 1000 * 60) / 1000;
        int ms = bestTime % 1000;
        return String.format("%d:%02d:%03d",min,sec,ms);
    }
}

class F1Race{
    List<Driver> drivers;

    public F1Race() {
        drivers = new ArrayList<>();
    }

    public void readResults(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("\\s+");
                    Driver driver = new Driver(parts[0]);
                    driver.addLap(parts[1]);
                    driver.addLap(parts[2]);
                    driver.addLap(parts[3]);
                    driver.calcBestTime();
                    drivers.add(driver);
                });
    }

    public void printSorted(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        AtomicInteger i = new AtomicInteger(1);
        drivers.stream()
                .sorted(Comparator.comparing(Driver::getBestTime))
                .forEach(driver ->{
                    pw.printf("%d. %-10s%10s\n",
                            i.getAndIncrement(),
                            driver.getName(),
                            driver.timeToString());
                });
        pw.flush();
    }
}

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
