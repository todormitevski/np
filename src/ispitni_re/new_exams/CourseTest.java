//package ispitni_re.new_exams;
//
//import java.util.*;
//import java.util.stream.Collectors;
//
//class InvalidPtsException extends Exception{
//    public InvalidPtsException() {
//    }
//}
//
//class Student{
//    String index;
//    String name;
//    int firstMidtermPts;
//    int secondMidtermPts;
//    int labPts;
//    int grade;
//
//    public Student(String index, String name) {
//        this.index = index;
//        this.name = name;
//        firstMidtermPts = 0;
//        secondMidtermPts = 0;
//        labPts = 0;
//        grade = 0;
//    }
//
//    public String getIndex() {
//        return index;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public int getFirstMidtermPts() {
//        return firstMidtermPts;
//    }
//
//    public int getSecondMidtermPts() {
//        return secondMidtermPts;
//    }
//
//    public int getLabPts() {
//        return labPts;
//    }
//
//    public int getGrade() {
//        calcGrade();
//        return grade;
//    }
//
//    public void setFirstMidtermPts(int firstMidtermPts) {
//        this.firstMidtermPts += firstMidtermPts;
//        if(this.firstMidtermPts > 100)
//            this.firstMidtermPts = 100;
//    }
//
//    public void setSecondMidtermPts(int secondMidtermPts) {
//        this.secondMidtermPts += secondMidtermPts;
//        if(this.secondMidtermPts > 100)
//            this.secondMidtermPts = 100;
//    }
//
//    public void setLabPts(int labPts) {
//        this.labPts += labPts;
//        if(this.labPts > 10)
//            this.labPts = 10;
//    }
//
//    public double calcPoints(){
//        return firstMidtermPts * 0.45 + secondMidtermPts * 0.45 + labPts;
//    }
//
//    public void calcGrade(){
//        double pts = calcPoints();
//        if(pts < 50)
//            grade = 5;
//        else if(pts >= 50 && pts < 60)
//            grade = 6;
//        else if(pts >= 60 && pts < 70)
//            grade = 7;
//        else if(pts >= 70 && pts < 80)
//            grade = 8;
//        else if(pts >= 80 && pts < 90)
//            grade = 9;
//        else grade = 10;
//    }
//
//    @Override
//    public String toString() {
//        return String.format(
//                "ID: %s Name: %s First midterm: %d " +
//                        "Second midterm %d Labs: %d " +
//                        "Summary points: %.2f Grade: %d",
//                index,name,firstMidtermPts,secondMidtermPts,
//                labPts,calcPoints(),getGrade()
//        );
//    }
//}
//
//class AdvancedProgrammingCourse{
//    Map<String,Student> students;
//    Map<Integer,Integer> gradesCount;
//
//    public AdvancedProgrammingCourse() {
//        students = new HashMap<>();
//        gradesCount = new HashMap<>();
//        gradesCount.put(5,0);
//        gradesCount.put(6,0);
//        gradesCount.put(7,0);
//        gradesCount.put(8,0);
//        gradesCount.put(9,0);
//        gradesCount.put(10,0);
//    }
//
//    public void addStudent(Student s){
//        students.putIfAbsent(s.getIndex(),s);
//    }
//
//    public void updateStudent(String idNumber, String activity, int points) throws InvalidPtsException {
//        Student student = students.get(idNumber);
//        switch (activity) {
//            case "midterm1":
//                if (points < 0 || (student.getFirstMidtermPts() + points) > 100)
//                    throw new InvalidPtsException();
//                else student.setFirstMidtermPts(points);
//                break;
//            case "midterm2":
//                if (points < 0 || (student.getSecondMidtermPts() + points) > 100)
//                    throw new InvalidPtsException();
//                else student.setSecondMidtermPts(points);
//                break;
//            case "labs":
//                if (points < 0 || (student.getLabPts() + points) > 10)
//                    throw new InvalidPtsException();
//                else student.setLabPts(points);
//                break;
//            default: throw new InvalidPtsException();
//        }
//    }
//
//    public List<Student> getFirstNStudents(int n){
//        return students.values().stream()
//                .sorted(Comparator.comparing(Student::calcPoints)
//                        .reversed())
//                //.filter(i -> i.calcPoints() >= 50)
//                .limit(n)
//                .collect(Collectors.toList());
//    }
//
//    public void fillGradesMap(){
//        for(Student student : students.values()){
//            gradesCount.computeIfPresent(student.getGrade(),
//                    (k,v) -> ++v);
//        }
//    }
//
//    public Map<Integer,Integer> getGradeDistribution(){
//        fillGradesMap();
//        return gradesCount;
//    }
//
//    public void printStatistics(){
//        DoubleSummaryStatistics dss = new DoubleSummaryStatistics();
//        for(Student student : students.values()){
//            if(student.calcPoints() >= 50)
//                dss.accept(student.calcPoints());
//        }
//        System.out.printf(
//                "Count: %d Min: %.2f Average: %.2f Max: %.2f%n",
//                dss.getCount(),dss.getMin(),dss.getAverage(),dss.getMax()
//        );
//    }
//}
//
//public class CourseTest {
//
//    public static void printStudents(List<Student> students) {
//        students.forEach(System.out::println);
//    }
//
//    public static void printMap(Map<Integer, Integer> map) {
//        map.forEach((k, v) -> System.out.printf("%d -> %d%n", k, v));
//    }
//
//    public static void main(String[] args) {
//        AdvancedProgrammingCourse advancedProgrammingCourse = new AdvancedProgrammingCourse();
//
//        Scanner sc = new Scanner(System.in);
//
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String[] parts = line.split("\\s+");
//
//            String command = parts[0];
//
//            if (command.equals("addStudent")) {
//                String id = parts[1];
//                String name = parts[2];
//                advancedProgrammingCourse.addStudent(new Student(id, name));
//            } else if (command.equals("updateStudent")) {
//                String idNumber = parts[1];
//                String activity = parts[2];
//                int points = Integer.parseInt(parts[3]);
//                try {
//                    advancedProgrammingCourse.updateStudent(idNumber, activity, points);
//                } catch (InvalidPtsException ignored) {}
//            } else if (command.equals("getFirstNStudents")) {
//                int n = Integer.parseInt(parts[1]);
//                printStudents(advancedProgrammingCourse.getFirstNStudents(n));
//            } else if (command.equals("getGradeDistribution")) {
//                printMap(advancedProgrammingCourse.getGradeDistribution());
//            } else {
//                advancedProgrammingCourse.printStatistics();
//            }
//        }
//    }
//}
