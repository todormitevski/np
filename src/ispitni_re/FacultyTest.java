//package ispitni_re;
//
//import java.util.*;
//
////IMPOSSIBLE
//
//class OperationNotAllowedException extends Exception{
//    public OperationNotAllowedException(String message) {
//        super(message);
//    }
//}
//
//class Course{
//    String name;
//    int grade;
//    IntSummaryStatistics stats;
//
//    public Course(String name, int grade) {
//        this.name = name;
//        this.grade = grade;
//        stats = new IntSummaryStatistics();
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    int getNumStudents(){
//        return (int) stats.getCount();
//    }
//
//    double getCourseAvgGrade(){
//        return stats.getAverage();
//    }
//}
//
//class Student{
//    String id;
//    int yearsOfStudies;
//    Map<Integer,Set<Course>> coursesByTerm;
//    Set<String> allCourses = new TreeSet<>(Comparator.naturalOrder());
//
//    public Student(String id, int yearsOfStudies) {
//        this.id = id;
//        this.yearsOfStudies = yearsOfStudies;
//        coursesByTerm = new TreeMap<>();
//    }
//
//    public double calcAvgGrade(){
//        Set<Course> allCourses = new HashSet<>();
//        coursesByTerm.values()
//                .forEach(allCourses::addAll);
//        return allCourses.stream()
//                .mapToDouble(i -> i.grade)
//                .average()
//                .orElse(0);
//    }
//
//    public double calcAvgGradeForTerm(int term){
//        Set<Course> courses = coursesByTerm.get(term);
//        return courses.stream()
//                .mapToDouble(i -> i.grade)
//                .average()
//                .orElse(0);
//    }
//
//    public int getNumCoursesPassed(){
//        List<Course> allCourses = new ArrayList<>();
//        coursesByTerm.values()
//                .forEach(allCourses::addAll);
//        return allCourses.size();
//    }
//
//    public String getDetailedReport(){
//        StringBuilder sb = new StringBuilder();
//        sb.append(id).append("\n");
//        coursesByTerm.keySet()
//                .forEach(key -> sb.append(getDetailedReportForTerm(key))
//                        .append("\n"));
//        sb.append(String.format(
//                "Average grade: %.2f\n",
//                calcAvgGrade()
//        ));
//        sb.append(String.format(
//                "Courses attended: %s",
//                String.join(",", allCourses)
//        ));
//        return sb.toString();
//    }
//
//    public String getDetailedReportForTerm(int term){
//        return String.format(
//                "Term %d\nCourses: %d\nAverage grade for term %.2f",
//                term,
//                coursesByTerm.get(term).size(),
//                calcAvgGradeForTerm(term)
//        );
//    }
//
//    public String getShortReport(){
//        return String.format(
//                "Student: %s Courses passed: %d Average grade: %.2f",
//                id,getNumCoursesPassed(),calcAvgGrade()
//        );
//    }
//
//    public String getId() {
//        return id;
//    }
//}
//
//class Faculty{
//    Map<String,Student> students;
//    Map<String,Course> coursesByName;
//    List<String> logs;
//
//    public Faculty() {
//        students = new TreeMap<>();
//        coursesByName = new HashMap<>();
//        logs = new ArrayList<>();
//    }
//
//    public void addStudent(String id, int yearsOfStudies) {
//        students.putIfAbsent(id,new Student(id,yearsOfStudies));
//    }
//
//    public void detectGraduation(Student student){
//        if((student.yearsOfStudies == 3 && student.getNumCoursesPassed() == 18) ||
//                (student.yearsOfStudies == 4 && student.getNumCoursesPassed() == 24)){
//            logs.add(String.format(
//                    "Student with ID %s graduated with average grade %.2f in %d years",
//                    student.id,student.calcAvgGrade(),student.yearsOfStudies
//            ));
//            students.remove(student.id);
//        }
//    }
//
//    public void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
//        Student student = students.get(studentId);
//        if(student.coursesByTerm.size() != 0){
//            if(student.coursesByTerm.get(term).size() == 3){
//                throw new OperationNotAllowedException(String.format(
//                        "Student %s already has 3 grades in term %d",
//                        studentId,term
//                ));
//            } else{
//                if((student.yearsOfStudies == 3 && term > 6) ||
//                        (student.yearsOfStudies == 4 && term > 8)){
//                    throw new OperationNotAllowedException(String.format(
//                            "Term %d is not possible for student with ID %s",
//                            term,studentId
//                    ));
//                } else{
//                    student.coursesByTerm.computeIfPresent(term, (k,v) ->{
//                        v.add(new Course(courseName,grade));
//                        return v;
//                    });
//                    addAbsentStudent(term, courseName, grade, student);
//                }
//                detectGraduation(student);
//            }
//        } else{
//            addAbsentStudent(term, courseName, grade, student);
//        }
//    }
//
//    private void addAbsentStudent(int term, String courseName, int grade, Student student) {
//        Set<Course> courses = new HashSet<>();
//        courses.add(new Course(courseName,grade));
//        student.coursesByTerm.putIfAbsent(term,courses);
//        student.allCourses.add(courseName);
//        coursesByName.putIfAbsent(courseName,new Course(courseName,grade));
//        coursesByName.get(courseName).stats.accept(grade);
//    }
//
//    public String getFacultyLogs() {
//        StringBuilder sb = new StringBuilder();
//        for(String log : logs){
//            sb.append(log).append("\n");
//        }
//        return sb.toString();
//    }
//
//    public String getDetailedReportForStudent(String id) {
//        return students.get(id).getDetailedReport();
//    }
//
//    public void printFirstNStudents(int n) {
//        Comparator<Student> comparator = Comparator.comparing(Student::getNumCoursesPassed)
//                .thenComparing(Student::calcAvgGrade)
//                .thenComparing(Student::getId).reversed();
//        TreeSet<Student> studentsSorted = new TreeSet<>(comparator);
//        studentsSorted.addAll(students.values());
//        studentsSorted.stream()
//                .limit(n)
//                .forEach(student -> System.out.println(student.getShortReport()));
//    }
//
//    public void printCourses() {
//        Comparator<Course> comparator = Comparator.comparing(Course::getNumStudents)
//                .thenComparing(Course::getCourseAvgGrade)
//                .thenComparing(Course::getName);
//        TreeSet<Course> coursesSet = new TreeSet<>(comparator);
//        coursesSet.addAll(coursesByName.values());
//        coursesSet.forEach(System.out::println);
//    }
//}
//
//public class FacultyTest {
//
//    public static void main(String[] args) {
//        Scanner sc = new Scanner(System.in);
//        int testCase = sc.nextInt();
//
//        if (testCase == 1) {
//            System.out.println("TESTING addStudent AND printFirstNStudents");
//            Faculty faculty = new Faculty();
//            for (int i = 0; i < 10; i++) {
//                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
//            }
//            faculty.printFirstNStudents(10);
//
//        } else if (testCase == 2) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            try {
//                faculty.addGradeToStudent("123", 7, "NP", 10);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//            try {
//                faculty.addGradeToStudent("1234", 9, "NP", 8);
//            } catch (OperationNotAllowedException e) {
//                System.out.println(e.getMessage());
//            }
//        } else if (testCase == 3) {
//            System.out.println("TESTING addGrade and exception");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//            for (int i = 0; i < 4; i++) {
//                try {
//                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
//                } catch (OperationNotAllowedException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//        } else if (testCase == 4) {
//            System.out.println("Testing addGrade for graduation");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("123", 3);
//            faculty.addStudent("1234", 4);
//            int counter = 1;
//            for (int i = 1; i <= 6; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            counter = 1;
//            for (int i = 1; i <= 8; i++) {
//                for (int j = 1; j <= 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
//                    } catch (OperationNotAllowedException e) {
//                        System.out.println(e.getMessage());
//                    }
//                    ++counter;
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
//            faculty.printFirstNStudents(2);
//        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
//            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            if (testCase == 5)
//                faculty.printFirstNStudents(10);
//            else if (testCase == 6)
//                faculty.printFirstNStudents(3);
//            else
//                faculty.printFirstNStudents(20);
//        } else if (testCase == 8 || testCase == 9) {
//            System.out.println("TESTING DETAILED REPORT");
//            Faculty faculty = new Faculty();
//            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
//            int grade = 6;
//            int counterCounter = 1;
//            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
//                for (int j = 1; j < 3; j++) {
//                    try {
//                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
//                    } catch (OperationNotAllowedException e) {
//                        e.printStackTrace();
//                    }
//                    grade++;
//                    if (grade == 10)
//                        grade = 5;
//                    ++counterCounter;
//                }
//            }
//            System.out.println(faculty.getDetailedReportForStudent("student1"));
//        } else if (testCase==10) {
//            System.out.println("TESTING PRINT COURSES");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            faculty.printCourses();
//        } else if (testCase==11) {
//            System.out.println("INTEGRATION TEST");
//            Faculty faculty = new Faculty();
//            for (int i = 1; i <= 10; i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//
//            }
//
//            for (int i=11;i<15;i++) {
//                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
//                int courseCounter = 1;
//                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
//                    for (int k = 1; k <= 3; k++) {
//                        int grade = sc.nextInt();
//                        try {
//                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
//                        } catch (OperationNotAllowedException e) {
//                            System.out.println(e.getMessage());
//                        }
//                        ++courseCounter;
//                    }
//                }
//            }
//            System.out.println("LOGS");
//            System.out.println(faculty.getFacultyLogs());
//            System.out.println("DETAILED REPORT FOR STUDENT");
//            System.out.println(faculty.getDetailedReportForStudent("student2"));
//            try {
//                System.out.println(faculty.getDetailedReportForStudent("student11"));
//                System.out.println("The graduated students should be deleted!!!");
//            } catch (NullPointerException e) {
//                System.out.println("The graduated students are really deleted");
//            }
//            System.out.println("FIRST N STUDENTS");
//            faculty.printFirstNStudents(10);
//            System.out.println("COURSES");
//            faculty.printCourses();
//        }
//    }
//}
