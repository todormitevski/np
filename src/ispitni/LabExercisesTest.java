package ispitni;

import java.util.*;
import java.util.stream.Collectors;

class Student{
    String index;
    List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;
        this.points = points;
    }

    public String getIndex() {
        return index;
    }

    public List<Integer> getPoints() {
        return points;
    }

    public double sumPoints(){
        return (double) points.stream()
                .mapToInt(i -> i)
                .sum() / 10;
    }

    public boolean signature(){
        return points.size() >= 8;
    }

    public int getYear(){
        return 20 - Integer.parseInt(index.substring(0,2));
    }

    @Override
    public String toString() {
        String signature = "";
        if(signature()){
            signature = "YES";
        } else signature = "NO";
        return String.format(
                "%s %s %.2f",
                index,signature,sumPoints()
        );
    }
}

class LabExercises{
    Collection<Student> students;

    public LabExercises() {
        students = new ArrayList<>();
    }

    public void addStudent (Student student){
        students.add(student);
    }

    public void printByAveragePoints (boolean ascending, int n){
        if(ascending){
            students.stream()
                    .sorted(Comparator.comparing(Student::sumPoints)
                            .thenComparing(Student::getIndex))
                    .limit(n)
                    .forEach(System.out::println);
        } else{
            students.stream()
                    .sorted(Comparator.comparing(Student::sumPoints)
                            .thenComparing(Student::getIndex).reversed())
                    .limit(n)
                    .forEach(System.out::println);
        }
    }

    public List<Student> failedStudents (){
        List<Student> failedStudents = new ArrayList<Student>();
        students.stream()
                .filter(student -> !student.signature())
                .sorted(Comparator.comparing(Student::getIndex)
                        .thenComparing(Student::sumPoints))
                .forEach(failedStudents::add);
        return failedStudents;
    }

    public Map<Integer,Double> getStatisticsByYear(){
        Map<Integer,Double> grades = new HashMap<Integer,Double>();
        Map<Integer,Integer> numOfStudentsForYear = new HashMap<Integer,Integer>();
        students.stream()
                .filter(Student::signature)
                .forEach(student ->{
                    int year = student.getYear();
                    double points = student.sumPoints();
                    grades.computeIfPresent(year, (k,v) -> v += points);
                    grades.putIfAbsent(year,points);
                    numOfStudentsForYear.computeIfPresent(year, (k,v) -> v += 1);
                    numOfStudentsForYear.putIfAbsent(year,1);
                });
        Map<Integer,Double> finalMap = new HashMap<Integer,Double>();
        for(Map.Entry<Integer,Double> entry : grades.entrySet()){
            finalMap.put(entry.getKey(), entry.getValue() / numOfStudentsForYear.get(entry.getKey()));
        }
        return finalMap;
    }
}

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {
            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}
