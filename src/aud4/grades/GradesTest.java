package aud4.grades;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class GradesTest {

    public static void main(String[] args) {
        Course course = new Course();
        File inputFile = new File("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\aud4\\files\\input.txt");
        File outputFile = new File("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\aud4\\files\\output.txt");

        try {

            course.readData(new FileInputStream(inputFile));
            System.out.println("===PRINT SORTED STUDENTS TO SCREEN===");
            course.printSortedData(System.out);

            System.out.println("===PRINT DETAILED REPORT TO FILE===");
            course.printDetailedData(new FileOutputStream(outputFile));

            System.out.println("===PRINT GRADE DIST TO SCREEN===");
            course.printDistribution(System.out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
