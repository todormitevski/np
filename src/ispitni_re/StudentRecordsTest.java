package ispitni_re;

import java.io.*;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * January 2016 Exam problem 1
 */

class Student{
    String code;
    List<Integer> grades;

    public Student(String code, List<Integer> grades) {
        this.code = code;
        this.grades = grades;
    }

    public String getCode() {
        return code;
    }

    public double calcAvgGrade(){
        return grades.stream()
                .mapToDouble(i -> i)
                .average()
                .orElse(0);
    }
}

class StudentRecords{
    Map<String,List<Student>> studentsMap;

    public StudentRecords() {
        studentsMap = new TreeMap<>();
    }

    public int readRecords(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        br.lines()
                .forEach(line ->{
                    String[] parts = line.split("\\s+");
                    String code = parts[0];
                    String path = parts[1];
                    List<Integer> grades = new ArrayList<>();
                    for(int i=2;i<parts.length;i++){
                        grades.add(Integer.parseInt(parts[i]));
                    }
                    Student student = new Student(code,grades);
                    List<Student> studentList = new ArrayList<>();
                    studentList.add(student);
                    studentsMap.computeIfPresent(path, (k,v) ->{
                        v.add(student);
                        return v;
                    });
                    studentsMap.putIfAbsent(path,studentList);
                });
        AtomicInteger count = new AtomicInteger();
        studentsMap.values()
                .forEach(value ->{
                    count.addAndGet(value.size());
                });
        return count.intValue();
    }

    public void writeTable(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        studentsMap.entrySet()
                .stream()
                .sorted(Map.Entry.comparingByKey())
                .forEach(entry ->{
                    StringBuilder sb = new StringBuilder();
                    sb.append(entry.getKey()).append("\n");
                    entry.getValue().stream()
                            .sorted(Comparator.comparing(Student::calcAvgGrade)
                                    .reversed()
                                    .thenComparing(Student::getCode))
                            .forEach(student ->{
                                sb.append(String.format(
                                        "%s %.2f\n",
                                        student.getCode(),
                                        student.calcAvgGrade()
                                ));
                            });
                    pw.print(sb);
                });
        pw.flush();
    }

    public int CountAs(List<Student> students){
        int countA = 0;
        for(Student student : students) {
            for (Integer grade : student.grades) {
                if (grade == 10)
                    countA += grade;
            }
        }
        return countA;
    }

    public void writeDistribution(OutputStream outputStream) {
        PrintWriter pw = new PrintWriter(outputStream);
        studentsMap.entrySet()
                .stream()
                .sorted((i,j) ->{
                    int countAsI = CountAs(i.getValue());
                    int countAsJ = CountAs(j.getValue());
                    return Integer.compare(countAsJ,countAsI);
                })
                .forEach(entry ->{
                    String path = entry.getKey();
                    List<Student> students = entry.getValue();
                    int countA = 0;
                    int countB = 0;
                    int countC = 0;
                    int countD = 0;
                    int countE = 0;
                    for(Student student : students){
                        for(int grade : student.grades){
                            if(grade == 10)
                                countA++;
                            if(grade == 9)
                                countB++;
                            if(grade == 8)
                                countC++;
                            if(grade == 7)
                                countD++;
                            if(grade == 6)
                                countE++;
                        }
                    }
                    StringBuilder sb = new StringBuilder();

                    sb.append(String.format("%s\n",entry.getKey()));

                    sb.append(String.format("%2d | ",6));
                    sb.append("*".repeat(Math.max(0, countE / 10)));
                    if(countE % 10 > 0)
                        sb.append("*");
                    sb.append(String.format("(%d)\n",countE));

                    sb.append(String.format("%2d | ",7));
                    sb.append("*".repeat(Math.max(0, countD / 10)));
                    if(countD % 10 > 0)
                        sb.append("*");
                    sb.append(String.format("(%d)\n",countD));

                    sb.append(String.format("%2d | ",8));
                    sb.append("*".repeat(Math.max(0, countC / 10)));
                    if(countC % 10 > 0)
                        sb.append("*");
                    sb.append(String.format("(%d)\n",countC));

                    sb.append(String.format("%2d | ",9));
                    sb.append("*".repeat(Math.max(0, countB / 10)));
                    if(countB % 10 > 0)
                        sb.append("*");
                    sb.append(String.format("(%d)\n",countB));

                    sb.append(String.format("%2d | ",10));
                    sb.append("*".repeat(Math.max(0, countA / 10)));
                    if(countA % 10 > 0)
                        sb.append("*");
                    sb.append(String.format("(%d)\n",countA));

                    pw.print(sb);
                });
        pw.flush();
    }
}

public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = new StudentRecords();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords.writeDistribution(System.out);
    }
}
