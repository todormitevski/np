package ispitni_re.old_exams;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.function.Supplier;

abstract class Employee implements Comparable<Employee>{
    String id;
    String level;

    public Employee(String id, String level) {
        this.id = id;
        this.level = level;
    }

    public String getId() {
        return id;
    }

    public String getLevel() {
        return level;
    }

    public abstract double calcWage();

    @Override
    public int compareTo(Employee o) {
        return Comparator
                .comparing(Employee::calcWage)
                .thenComparing(Employee::getLevel)
                .reversed()
                .compare(this,o);
    }
}

class HourlyEmployee extends Employee{
    double hours;
    double hourlyRate;

    public HourlyEmployee(String id, String level, double hours, double hourlyRate) {
        super(id, level);
        this.hours = hours;
        this.hourlyRate = hourlyRate;
    }

    public double getHours() {
        return hours;
    }

    @Override
    public double calcWage() {
        if(hours > 40)
            return 40 * hourlyRate + (hours - 40) * hourlyRate * 1.5;
        return hours * hourlyRate;
    }

    @Override
    public String toString() {
        return String.format(
                "Employee ID: %s Level: %s Salary: %.2f Regular hours: %.2f Overtime hours: %.2f",
                id,level,calcWage(),
                hours <= 40 ? hours : 40,
                hours <= 40 ? 0 : hours - 40
        );
    }
}

class FreelanceEmployee extends Employee{
    List<Integer> ticketPoints;
    double ticketRate;

    public FreelanceEmployee(String id, String level, List<Integer> ticketPoints, double ticketRate) {
        super(id, level);
        this.ticketPoints = ticketPoints;
        this.ticketRate = ticketRate;
    }

    public List<Integer> getTicketPoints() {
        return ticketPoints;
    }

    @Override
    public double calcWage() {
        return ticketPoints.stream().mapToInt(i -> i).sum() * ticketRate;
    }

    @Override
    public String toString() {
        return String.format(
                "Employee ID: %s Level: %s Salary: %.2f Tickets count: %d Tickets points: %d",
                id,level,calcWage(),ticketPoints.size(),ticketPoints.stream().mapToInt(i -> i).sum()
        );
    }
}

class PayrollSystem{
    List<Employee> employees;
    Map<String,Double> hourlyRateByLevel;
    Map<String,Double> ticketRateByLevel;

    PayrollSystem(Map<String,Double> hourlyRateByLevel, Map<String,Double> ticketRateByLevel){
        employees = new ArrayList<>();
        this.hourlyRateByLevel = hourlyRateByLevel;
        this.ticketRateByLevel = ticketRateByLevel;
    }


    public void readEmployees(InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        br.lines()
                .forEach(line -> {
                    String[] parts = line.split(";");
                    String type = parts[0];
                    String id = parts[1];
                    String level = parts[2];
                    if(type.equals("F")){
                        List<Integer> ticketPts = new ArrayList<>();
                        for(int i=3;i<=parts.length-1;i++)
                            ticketPts.add(Integer.parseInt(parts[i]));
                        employees.add(
                                new FreelanceEmployee(id,level,ticketPts,ticketRateByLevel.get(level))
                        );
                    } else employees.add(
                            new HourlyEmployee(id,level,Double.parseDouble(parts[3]),hourlyRateByLevel.get(level))
                    );
                });
    }

    public Map<String, Set<Employee>> printEmployeesByLevels(OutputStream os, Set<String> levels) {
//        Map<String,Set<Employee>> result = new HashMap<>();
//
//        levels.forEach(level -> {
//            Set<Employee> employeesForLevel = employees.stream()
//                    .filter(employee -> employee.getLevel().equals(level))
//                    .sorted()
//                    .collect(Collectors.toCollection(LinkedHashSet::new));
//            result.put(level,employeesForLevel);
//        });

        Map<String, Set<Employee>> result = employees
                .stream()
                .collect(Collectors.groupingBy(Employee::getLevel,
                        (Supplier<TreeMap<String, Set<Employee>>>)TreeMap::new,
                        Collectors.toCollection(TreeSet::new)));

        Set<String> keys = new HashSet<>(result.keySet());
        keys.stream()
                .filter(key -> !levels.contains(key))
                .forEach(result::remove);

        return result;
    }
}

public class PayrollSystemTest {

    public static void main(String[] args) {

        Map<String, Double> hourlyRateByLevel = new LinkedHashMap<>();
        Map<String, Double> ticketRateByLevel = new LinkedHashMap<>();
        for (int i = 1; i <= 10; i++) {
            hourlyRateByLevel.put("level" + i, 10 + i * 2.2);
            ticketRateByLevel.put("level" + i, 5 + i * 2.5);
        }

        PayrollSystem payrollSystem = new PayrollSystem(hourlyRateByLevel, ticketRateByLevel);

        System.out.println("READING OF THE EMPLOYEES DATA");
        payrollSystem.readEmployees(System.in);

        System.out.println("PRINTING EMPLOYEES BY LEVEL");
        Set<String> levels = new LinkedHashSet<>();
        for (int i=5;i<=10;i++) {
            levels.add("level"+i);
        }
        Map<String, Set<Employee>> result = payrollSystem.printEmployeesByLevels(System.out, levels);
        result.forEach((level, employees) -> {
            System.out.println("LEVEL: "+ level);
            System.out.println("Employees: ");
            employees.forEach(System.out::println);
            System.out.println("------------");
        });


    }
}
