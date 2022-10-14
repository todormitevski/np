package aud1;

import java.util.Objects;

public class Person {
    private String name;
    private String surname;
    private int age;

    public Person() {
    }

    public Person(String name, String surname, int age) {
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", age=" + age +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return age == person.age && Objects.equals(name, person.name) && Objects.equals(surname, person.surname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, surname, age);
    }

    public static void main(String[] args) {
        Person p=new Person(); //needs default
        Person p1=new Person("Genji", "Shimada", 35); //needs argumental
        Person p2=new Person("Genji", "Shimada", 35); //needs argumental
        //Person p2=new Person("Hanzo", "Shimada", 38);
        System.out.println(p1);
        //if(p1==p2) System.out.println("true"); else System.out.println("false"); //compares references in memory
        if(p1.equals(p2)) System.out.println("true"); else System.out.println("false"); //correct way
        //strings are also compared with equals
    }
}
