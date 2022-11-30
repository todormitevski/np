package vezbi.audReRun.aud1;

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
        Person person1 = new Person(); //uses default
        Person person2 = new Person("David", "Monster", 25); //uses argumental
        Person person3 = new Person("David", "Monster", 25); //uses argumental

        if (person2.equals(person3)){ //comparing references without equals()
            System.out.println("true");
        } else System.out.println("false");

        System.out.println(person2); //prints a hexa value representing a pointer without the toString method
    }
}
