package kolokviumski;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

class Temperature{
    int day;
    List<Double> temperatures;
    DoubleSummaryStatistics summaryStats;

    public Temperature(int day, List<Double> temperatures) {
        this.day = day;
        this.temperatures = temperatures;
        summaryStats = temperatures.stream()
                .collect(Collectors.summarizingDouble(temp -> temp));
    }

    public int getDay() {
        return day;
    }

    public static double toCelsius(double fahrenheit){
        return (fahrenheit - 32) * 5 / 9;
    }

    public static double toFahrenheit(double celsius){
        return (celsius * 9) / 5 + 32;
    }

    public static Temperature createTemp(String line){
        String[] parts = line.split("\\s+");
        int day = Integer.parseInt(parts[0]);
        List<Double> temps = new ArrayList<>();
        for(int i=1;i< parts.length;i++){
            if(parts[i].endsWith("C")){
                temps.add(Double.valueOf(parts[i].substring(0,parts[i].length()-1)));
            } else{
                temps.add(toCelsius(Double.parseDouble(parts[i].substring(0, parts[i].length()-1))));
            }
        }
        return new Temperature(day,temps);
    }

    public String summary(char c){
        double min = summaryStats.getMin();
        double max = summaryStats.getMax();
        double avg = summaryStats.getAverage();
        if(c == 'F'){
            min = toFahrenheit(min);
            max = toFahrenheit(max);
            avg = toFahrenheit(avg);
        }
        return String.format(
                "Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                summaryStats.getCount(),min,c,max,c,avg,c
        );
    }

    @Override
    public String toString() {
        return String.format("%3d", day);
    }
}

class DailyTemperatures {
    List<Temperature> temperatures;

    public DailyTemperatures() {
        temperatures = new ArrayList<>();
    }

    void readTemperatures(InputStream inputStream){
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        temperatures = br.lines()
                .map(Temperature::createTemp)
                .collect(Collectors.toList());
    }

    void writeDailyStats(OutputStream outputStream, char scale){
        PrintWriter pw = new PrintWriter(outputStream);
        temperatures.stream()
                .sorted(Comparator.comparing(Temperature::getDay))
                .forEach(each -> pw.println(String.format("%s: %s",each,each.summary(scale))));
        pw.flush();
    }
}

public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}