//package labs.faculty;
//
//import aud4.grades.Student;
//
//import java.sql.SQLOutput;
//import java.util.*;
//import java.util.function.BiFunction;
//
//class StudentOnCourse{
//    String studentId;
//    int totalPoints;
//    String courseId;
//
//    public StudentOnCourse(String studentId, int totalPoints, String courseId) {
//        this.studentId = studentId;
//        this.totalPoints = totalPoints;
//        this.courseId = courseId;
//    }
//
//    public String getStudentId() {
//        return studentId;
//    }
//
//    public int getTotalPoints() {
//        return totalPoints;
//    }
//
//    public String getCourseId() {
//        return courseId;
//    }
//
//    int getGrade(){
//        int grade = totalPoints / 10 + 1;
//        if(grade < 5){
//            grade = 5;
//        }
//        if(grade > 10){
//            grade = 10;
//        }
//        return grade;
//    }
//
//    @Override
//    public String toString() {
//        //213165 85 (9)
//        return String.format("%s %d (%d)",studentId,totalPoints,getGrade());
//    }
//
//    public String reportWithCourse() {
//        return String.format("%s %d (%d)",courseId,totalPoints,getGrade());
//    }
//}
//
//class Faculty {
//    Map<String, List<StudentOnCourse>> studentsByCourse = new HashMap<>();
//    Map<String, List<StudentOnCourse>> coursesByStudent = new HashMap<>();
//
//    void addInfo(String courseId, String studentId, int totalPoints) {
//        StudentOnCourse studentOnCourse = new StudentOnCourse(studentId, totalPoints, courseId);
//
//        studentsByCourse.putIfAbsent(courseId, new ArrayList<>());
//        studentsByCourse.get(courseId).add(studentOnCourse);
//
//        coursesByStudent.putIfAbsent(studentId, new ArrayList<>());
//        coursesByStudent.get(studentId).add(studentOnCourse);
//    }
//
//    void printCourseReport(String courseId, String comparatorStr, boolean descending) {
//        List<StudentOnCourse> students = studentsByCourse.get(courseId);
//        Comparator<StudentOnCourse> comparator;
//        if (comparatorStr.equalsIgnoreCase("byid")) {
//            comparator = Comparator.comparing(StudentOnCourse::getStudentId);
//        } else {
//            comparator = Comparator.comparing(StudentOnCourse::getGrade)
//                    .thenComparing(StudentOnCourse::getTotalPoints)
//                    .thenComparing(StudentOnCourse::getStudentId);
//        }
//
//        if (descending) {
//            comparator = comparator.reversed();
//        }
//
//        students.stream()
//                .sorted(comparator)
//                .forEach(System.out::println);
//    }
//
//    public void printStudentReport(String studentId) {
//        Comparator<StudentOnCourse> comparator = Comparator.comparing(StudentOnCourse::getCourseId);
//        coursesByStudent.get(studentId)
//                .stream()
//                .sorted(comparator)
//                .forEach(s -> System.out.println(s.reportWithCourse()));
//    }
//
//    public Map<Integer,Integer> gradeDistribution(String courseId) {
//        List<StudentOnCourse> studentOnCourses = studentsByCourse.get(courseId);
//        Map<Integer,Integer> countingMap = new TreeMap<>();
//
//        for(StudentOnCourse studentOnCourse : studentOnCourses) {
//            int grade = studentOnCourse.getGrade();
//            countingMap.putIfAbsent(grade,0);
//            countingMap.computeIfPresent(grade, new BiFunction<Integer, Integer, Integer>() {
//                @Override
//                public Integer apply(Integer key, Integer value){
//                    return ++value;
//                }
//            });
//        }
//        return countingMap;
//    }
//}
//
//public class FacultyTest {
//
//    public static void main(String[] args) {
////        Scanner sc = new Scanner(System.in);
////        int testCase = sc.nextInt();
////
////        if (testCase == 1) {
////            System.out.println("TESTING addStudent AND printFirstNStudents");
////            Faculty faculty = new Faculty();
////            for (int i = 0; i < 10; i++) {
////                faculty.addStudent("student" + i, (i % 2 == 0) ? 3 : 4);
////            }
////            faculty.printFirstNStudents(10);
////
////        } else if (testCase == 2) {
////            System.out.println("TESTING addGrade and exception");
////            Faculty faculty = new Faculty();
////            faculty.addStudent("123", 3);
////            faculty.addStudent("1234", 4);
////            try {
////                faculty.addGradeToStudent("123", 7, "NP", 10);
////            } catch (OperationNotAllowedException e) {
////                System.out.println(e.getMessage());
////            }
////            try {
////                faculty.addGradeToStudent("1234", 9, "NP", 8);
////            } catch (OperationNotAllowedException e) {
////                System.out.println(e.getMessage());
////            }
////        } else if (testCase == 3) {
////            System.out.println("TESTING addGrade and exception");
////            Faculty faculty = new Faculty();
////            faculty.addStudent("123", 3);
////            faculty.addStudent("1234", 4);
////            for (int i = 0; i < 4; i++) {
////                try {
////                    faculty.addGradeToStudent("123", 1, "course" + i, 10);
////                } catch (OperationNotAllowedException e) {
////                    System.out.println(e.getMessage());
////                }
////            }
////            for (int i = 0; i < 4; i++) {
////                try {
////                    faculty.addGradeToStudent("1234", 1, "course" + i, 10);
////                } catch (OperationNotAllowedException e) {
////                    System.out.println(e.getMessage());
////                }
////            }
////        } else if (testCase == 4) {
////            System.out.println("Testing addGrade for graduation");
////            Faculty faculty = new Faculty();
////            faculty.addStudent("123", 3);
////            faculty.addStudent("1234", 4);
////            int counter = 1;
////            for (int i = 1; i <= 6; i++) {
////                for (int j = 1; j <= 3; j++) {
////                    try {
////                        faculty.addGradeToStudent("123", i, "course" + counter, (i % 2 == 0) ? 7 : 8);
////                    } catch (OperationNotAllowedException e) {
////                        System.out.println(e.getMessage());
////                    }
////                    ++counter;
////                }
////            }
////            counter = 1;
////            for (int i = 1; i <= 8; i++) {
////                for (int j = 1; j <= 3; j++) {
////                    try {
////                        faculty.addGradeToStudent("1234", i, "course" + counter, (j % 2 == 0) ? 7 : 10);
////                    } catch (OperationNotAllowedException e) {
////                        System.out.println(e.getMessage());
////                    }
////                    ++counter;
////                }
////            }
////            System.out.println("LOGS");
////            System.out.println(faculty.getFacultyLogs());
////            System.out.println("PRINT STUDENTS (there shouldn't be anything after this line!");
////            faculty.printFirstNStudents(2);
////        } else if (testCase == 5 || testCase == 6 || testCase == 7) {
////            System.out.println("Testing addGrade and printFirstNStudents (not graduated student)");
////            Faculty faculty = new Faculty();
////            for (int i = 1; i <= 10; i++) {
////                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
////                int courseCounter = 1;
////                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
////                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
////                        try {
////                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), i % 5 + 6);
////                        } catch (OperationNotAllowedException e) {
////                            System.out.println(e.getMessage());
////                        }
////                        ++courseCounter;
////                    }
////                }
////            }
////            if (testCase == 5)
////                faculty.printFirstNStudents(10);
////            else if (testCase == 6)
////                faculty.printFirstNStudents(3);
////            else
////                faculty.printFirstNStudents(20);
////        } else if (testCase == 8 || testCase == 9) {
////            System.out.println("TESTING DETAILED REPORT");
////            Faculty faculty = new Faculty();
////            faculty.addStudent("student1", ((testCase == 8) ? 3 : 4));
////            int grade = 6;
////            int counterCounter = 1;
////            for (int i = 1; i < ((testCase == 8) ? 6 : 8); i++) {
////                for (int j = 1; j < 3; j++) {
////                    try {
////                        faculty.addGradeToStudent("student1", i, "course" + counterCounter, grade);
////                    } catch (OperationNotAllowedException e) {
////                        e.printStackTrace();
////                    }
////                    grade++;
////                    if (grade == 10)
////                        grade = 5;
////                    ++counterCounter;
////                }
////            }
////            System.out.println(faculty.getDetailedReportForStudent("student1"));
////        } else if (testCase==10) {
////            System.out.println("TESTING PRINT COURSES");
////            Faculty faculty = new Faculty();
////            for (int i = 1; i <= 10; i++) {
////                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
////                int courseCounter = 1;
////                for (int j = 1; j < ((i % 2 == 1) ? 6 : 8); j++) {
////                    for (int k = 1; k <= ((j % 2 == 1) ? 3 : 2); k++) {
////                        int grade = sc.nextInt();
////                        try {
////                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
////                        } catch (OperationNotAllowedException e) {
////                            System.out.println(e.getMessage());
////                        }
////                        ++courseCounter;
////                    }
////                }
////            }
////            faculty.printCourses();
////        } else if (testCase==11) {
////            System.out.println("INTEGRATION TEST");
////            Faculty faculty = new Faculty();
////            for (int i = 1; i <= 10; i++) {
////                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
////                int courseCounter = 1;
////                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
////                    for (int k = 1; k <= ((j % 2 == 1) ? 2 : 3); k++) {
////                        int grade = sc.nextInt();
////                        try {
////                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
////                        } catch (OperationNotAllowedException e) {
////                            System.out.println(e.getMessage());
////                        }
////                        ++courseCounter;
////                    }
////                }
////
////            }
////
////            for (int i=11;i<15;i++) {
////                faculty.addStudent("student" + i, ((i % 2) == 1 ? 3 : 4));
////                int courseCounter = 1;
////                for (int j = 1; j <= ((i % 2 == 1) ? 6 : 8); j++) {
////                    for (int k = 1; k <= 3; k++) {
////                        int grade = sc.nextInt();
////                        try {
////                            faculty.addGradeToStudent("student" + i, j, ("course" + courseCounter), grade);
////                        } catch (OperationNotAllowedException e) {
////                            System.out.println(e.getMessage());
////                        }
////                        ++courseCounter;
////                    }
////                }
////            }
////            System.out.println("LOGS");
////            System.out.println(faculty.getFacultyLogs());
////            System.out.println("DETAILED REPORT FOR STUDENT");
////            System.out.println(faculty.getDetailedReportForStudent("student2"));
////            try {
////                System.out.println(faculty.getDetailedReportForStudent("student11"));
////                System.out.println("The graduated students should be deleted!!!");
////            } catch (NullPointerException e) {
////                System.out.println("The graduated students are really deleted");
////            }
////            System.out.println("FIRST N STUDENTS");
////            faculty.printFirstNStudents(10);
////            System.out.println("COURSES");
////            faculty.printCourses();
//        }
//    }
//}
