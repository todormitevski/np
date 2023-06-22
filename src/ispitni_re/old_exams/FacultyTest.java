package ispitni_re.old_exams;

import java.util.*;
import java.util.stream.Collectors;

class OperationNotAllowedException extends Exception{

    public OperationNotAllowedException(String message) {
        super(message);
    }
}

class Course{
    String courseName;
    int grade;
    double avgGradeForCourse;
    int numPeopleStudyingThisCourse;

    public Course(String courseName, int grade) {
        this.courseName = courseName;
        this.grade = grade;
        this.avgGradeForCourse = 0.0;
        this.numPeopleStudyingThisCourse = 0;
    }

    public String getCourseName() {
        return courseName;
    }

    public int getGrade() {
        return grade;
    }

    public double getAvgGradeForCourse() {
        return avgGradeForCourse;
    }

    public void setAvgGradeForCourse(double avgGradeForCourse) {
        this.avgGradeForCourse = avgGradeForCourse;
    }

    public int getNumPeopleStudyingThisCourse() {
        return numPeopleStudyingThisCourse;
    }

    public void setNumPeopleStudyingThisCourse(int numPeopleStudyingThisCourse) {
        this.numPeopleStudyingThisCourse = numPeopleStudyingThisCourse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return grade == course.grade && Double.compare(course.avgGradeForCourse, avgGradeForCourse) == 0 && numPeopleStudyingThisCourse == course.numPeopleStudyingThisCourse && Objects.equals(courseName, course.courseName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseName, grade, avgGradeForCourse, numPeopleStudyingThisCourse);
    }
}

class Term{
    List<Course> courses;

    public Term() {
        this.courses = new ArrayList<>();
    }

    public void addCourse(String courseName, int grade){
        courses.add(new Course(courseName,grade));
    }

    public int getGradesForCourse(){
        int total = 0;
        for(Course course : courses){
            total += course.getGrade();
        }
        return total;
    }

    public int getCoursesSize(){
        return courses.size();
    }

    public double getAvgGradeForTerm(){
        if(courses.size() == 0)
            return 5.00;
        double total = 0.0;
        for(Course course: courses){
            total += course.getGrade();
        }
        return total / courses.size();
    }

    public List<Course> getCourses() {
        return courses;
    }
}

class Student{
    String id;
    int yearsOfStudies;
    List<Term> terms;

    public Student(String id, int yearsOfStudies) {
        this.id = id;
        this.yearsOfStudies = yearsOfStudies;
        this.terms = new ArrayList<>();

        if(yearsOfStudies == 3){
            for(int i=0;i<6;i++){
                terms.add(new Term());
            }
        } else{
            for(int i=0;i<8;i++){
                terms.add(new Term());
            }
        }
    }

    public String getId() {
        return id;
    }

    public int getYearsOfStudies() {
        return yearsOfStudies;
    }

    public Term getNthTerm(int index){
        return terms.get(index - 1);
    }

    public void addGradeToCourseForTerm(int term, String courseName, int grade){
        Term gottenTerm = terms.get(term - 1);
        gottenTerm.addCourse(courseName, grade);
    }

    public boolean checkGraduation(){
        int total = 0;
        for(Term term : terms){
            total += term.courses.size();
        }
        return (yearsOfStudies == 3 && total == 18) ||
                (yearsOfStudies == 4 && total == 24);
    }

    public double getAvgGrade(){
        double avg = 0.0;
        for(Term term : terms){
            avg += term.getGradesForCourse();
        }
//        if(yearsOfStudies == 3)
//            return avg / 18;
//        else return avg / 24;
        if(getNumCoursesCompleted() == 0)
            return 5.00;
        return avg / getNumCoursesCompleted();
    }

    public List<Course> collectCourses(){
        List<Course> finalList = new ArrayList<>();
        for(Term term : terms){
            List<Course> gottenList = term.getCourses();
            finalList.addAll(gottenList);
        }
        return finalList;
    }

    public int getNumCoursesCompleted(){
        return collectCourses().size();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("Student: %s\n", id));
        int i = 1;
        for(Term term : terms){
            sb.append(String.format("Term %d\n", i++));
            sb.append(String.format("Courses: %d\n",term.getCoursesSize()));
            sb.append(String.format("Average grade for term: %.2f\n",term.getAvgGradeForTerm()));
        }
        sb.append(String.format("Average grade: %.2f\n",getAvgGrade()));
        sb.append("Courses attended: ");
        List<Course> courseList = collectCourses();
        courseList.sort(Comparator.comparing(Course::getCourseName));
        StringJoiner sj = new StringJoiner(",");
        for(Course course : courseList){
            sj.add(course.getCourseName());
        }
        sb.append(sj);
        return sb.toString();
    }
}

class Faculty {
    Map<String,Student> students;
    List<String> logs;

    public Faculty() {
        this.students = new TreeMap<>();
        this.logs = new ArrayList<>();
    }

    void addStudent(String id, int yearsOfStudies) {
        students.putIfAbsent(id, new Student(id,yearsOfStudies));
    }

    void addGradeToStudent(String studentId, int term, String courseName, int grade) throws OperationNotAllowedException {
        Student student = students.get(studentId);
        if(student != null){
            if((student.getYearsOfStudies() == 3 && term > 6) ||
                    (student.getYearsOfStudies() == 4 && term > 8))
                throw new OperationNotAllowedException(
                        String.format(
                                "Term %d is not possible for student with ID %s",
                                term,studentId
                        )
                );

            if(student.getNthTerm(term).courses.size() < 3){
                student.addGradeToCourseForTerm(term,courseName,grade);
                if(student.checkGraduation()){
                    logs.add(String.format(
                            "Student with ID %s graduated with average grade %.2f in %d years.",
                            studentId,student.getAvgGrade(),student.getYearsOfStudies()
                    ));
                    students.remove(student.getId());
                }
            }
            else throw new OperationNotAllowedException(
                    String.format(
                            "Student %s already has 3 grades in term %d",
                            studentId,term
                    )
            );
        }
    }

    String getFacultyLogs() {
        StringJoiner sj = new StringJoiner("\n");
        for(String log : logs){
            sj.add(log);
        }
        return sj.toString();
    }

    String getDetailedReportForStudent(String id) {
        return students.get(id).toString();
    }

    void printFirstNStudents(int n) {
        StringJoiner sj = new StringJoiner("\n");
        List<Student> studentsList = students.values().stream()
                .collect(Collectors.toList());
        studentsList.stream()
                .sorted(Comparator.comparing(Student::getNumCoursesCompleted)
                        .thenComparing(Student::getAvgGrade)
                        .thenComparing(Student::getId).reversed())
                .limit(n)
                .forEach(student -> {
                    sj.add(String.format(
                            "Student: %s Courses passed: %d Average grade: %.2f",
                            student.getId(),
                            student.getNumCoursesCompleted(),
                            student.getAvgGrade()
                    ));
                });
        System.out.println(sj);
    }

    void printCourses() {
        Map<Course,Integer> coursePopularityMap = new HashMap<>();
        Map<Course,Double> courseSumOfGradesMap = new HashMap<>();

        Set<Course> allCoursesSet = new HashSet<>();

        for(Student student : students.values()){
            List<Course> gottenList = student.collectCourses();
            allCoursesSet.addAll(gottenList);
            for(Course course : gottenList){
                coursePopularityMap.computeIfPresent(course, (k,v) -> ++v);
                coursePopularityMap.putIfAbsent(course,1);
                courseSumOfGradesMap.computeIfPresent(course, (k,v) ->{
                    v += course.getGrade();
                    return v;
                });
                courseSumOfGradesMap.putIfAbsent(course, (double) course.getGrade());
            }
        }

        for(Course course : courseSumOfGradesMap.keySet()){
            int popularity = coursePopularityMap.get(course);
            double sumOfGradesForCourse = courseSumOfGradesMap.get(course);
            course.setNumPeopleStudyingThisCourse(popularity);
            course.setAvgGradeForCourse(sumOfGradesForCourse / popularity);
        }

        StringJoiner sj = new StringJoiner("\n");
        allCoursesSet.stream()
                .sorted(Comparator.comparing(Course::getNumPeopleStudyingThisCourse)
                        .thenComparing(Course::getAvgGradeForCourse))
                .forEach(course -> {
                    sj.add(String.format(
                            "%s %d %.2f",
                            course.getCourseName(),
                            course.getNumPeopleStudyingThisCourse(),
                            course.getAvgGradeForCourse()
                    ));
                });
        System.out.println(sj);
    }
}

public class FacultyTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int testCase = sc.nextInt();

        if (testCase == 1) {
            System.out.println("TESTING addStudent AND printFirstNStudents");
            Faculty faculty = new Faculty();
            for (int i = 0; i < 10; i++) {
                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
            }
            faculty.printFirstNStudents(10);

        } else if (testCase == 2) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            try {
                faculty.addGradeToStudent("123", 7, "NP", 10);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
            try {
                faculty.addGradeToStudent("1234", 9, "NP", 8);
            } catch (OperationNotAllowedException e) {
                System.out.println(e.getMessage());
            }
        } else if (testCase == 3) {
            System.out.println("TESTING addGrade and exception");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
            for (int i = 0; i < 4; i++) {
                try {
                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
                } catch (OperationNotAllowedException e) {
                    System.out.println(e.getMessage());
                }
            }
        } else if (testCase == 4) {
            System.out.println("Testing addGrade for graduation");
            Faculty faculty = new Faculty();
            faculty.addStudent("123", 3);
            faculty.addStudent("1234", 4);
            int counter = 1;
            for (int i = 1; i <= 6; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            counter = 1;
            for (int i = 1; i <= 8; i++) {
                for (int j = 1; j <= 3; j++) {
                    try {
                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
                    } catch (OperationNotAllowedException e) {
                        System.out.println(e.getMessage());
                    }
                    ++counter;
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
            faculty.printFirstNStudents(2);
        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            if (testCase == 5)
                faculty.printFirstNStudents(10);
            else if (testCase == 6)
                faculty.printFirstNStudents(3);
            else
                faculty.printFirstNStudents(20);
        } else if (testCase == 8 || testCase == 9) {
            System.out.println("TESTING DETAILED REPORT");
            Faculty faculty = new Faculty();
            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
            int grade = 6;
            int counterCounter = 1;
            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
                for (int j = 1; j < 3; j++) {
                    try {
                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
                    } catch (OperationNotAllowedException e) {
                        e.printStackTrace();
                    }
                    grade++;
                    if (grade == 10)
                        grade = 5;
                    ++counterCounter;
                }
            }
            System.out.println(faculty.getDetailedReportForStudent("student1"));
        } else if (testCase==10) {
            System.out.println("TESTING PRINT COURSES");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            faculty.printCourses();
        } else if (testCase==11) {
            System.out.println("INTEGRATION TEST");
            Faculty faculty = new Faculty();
            for (int i = 1; i <= 10; i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }

            }

            for (int i=11;i<15;i++) {
                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
                int courseCounter = 1;
                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
                    for (int k = 1; k <= 3; k++) {
                        int grade = sc.nextInt();
                        try {
                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
                        } catch (OperationNotAllowedException e) {
                            System.out.println(e.getMessage());
                        }
                        ++courseCounter;
                    }
                }
            }
            System.out.println("LOGS");
            System.out.println(faculty.getFacultyLogs());
            System.out.println("DETAILED REPORT FOR STUDENT");
            System.out.println(faculty.getDetailedReportForStudent("student2"));
            try {
                System.out.println(faculty.getDetailedReportForStudent("student11"));
                System.out.println("The graduated students should be deleted!!!");
            } catch (NullPointerException e) {
                System.out.println("The graduated students are really deleted");
            }
            System.out.println("FIRST N STUDENTS");
            faculty.printFirstNStudents(10);
            System.out.println("COURSES");
            faculty.printCourses();
        }
    }
}
