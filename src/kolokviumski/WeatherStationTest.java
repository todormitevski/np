package kolokviumski;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

//not finished

class Measurement{
    double temperature;
    double wind;
    double humidity;
    double visibility;
    Date date;

    public Measurement(double temperature, double wind, double humidity, double visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = date;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public double getWind() {
        return wind;
    }

    public double getVisibility() {
        return visibility;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return String.format("%.1f %.1f km/h %.1f%% %.1f km",
                temperature,
                wind,
                humidity,
                visibility);
    }
}

class WeatherStation{
    int days;
    List<Measurement> measurements;

    public WeatherStation(int days) {
        this.days = days;
        measurements = new ArrayList<>();
    }

    public int getDays() {
        return days;
    }

    public void addMeasurment(float temperature, float wind, float humidity, float visibility, Date date){
        Measurement measurement = new Measurement(temperature,wind,humidity,visibility,date);
        measurements.add(measurement);
    }

    public int total(){
        return measurements.size();
    }

    public void status(Date from, Date to){
        measurements.stream()
                .sorted(Comparator.comparing(Measurement::getDate))
                .forEach(System.out::println);
    }
}

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurment(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}
