package auds.aud9;

import java.util.*;

class Student /*implements Comparable<Student>*/{
    String id;
    int grades;

    public Student(String id, int grades) {
        this.id = id;
        this.grades = grades;
    }

//    @Override
//    public int compareTo(Student o) {
//        return 0;
//    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return grades == student.grades && Objects.equals(id, student.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, grades);
    }
}

public class SetIntro {
    public static void main(String[] args) {
        //sets
        Set<String> set;

        //O(1) add,remove,search           O(n) iterating
        //LinkedHashSet for keeping order, HashSet for no order
//        set = new HashSet<String>();
//        String s1 = "";
//        String s2 = "Stefan";
//
//        set.add(s1);
//        set.add(s2);
//        set.add("NP");
//        set.add("Napredno Programiranje");
//
//        System.out.println(s1.hashCode());
//        System.out.println(s2.hashCode());
//        System.out.println(set);

        //for ascending order lexicographic
        //O(logN) add,remove,search
        set = new TreeSet<>(String::compareToIgnoreCase);
        String s1 = "";
        String s2 = "Stefan";
        set.add("NP");
        set.add("NApredno Programiranje");
        set.add("Stefan");

        System.out.println(set);

        //Set<Student> students = new TreeSet<>();
    }
}
