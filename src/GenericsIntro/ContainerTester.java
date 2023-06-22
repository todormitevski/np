package GenericsIntro;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * The entire code is in comments because it needs fixes. Uncomment it and use it to solve the following problem.
 *
 * Problem:
 * Implement a generic class Container to store elements that can be weighed (implement Weightable interface).
 * For the class implement these functions:
 *
 *      Container() - constructor
 *      void addElement(T elem) - method that adds an element
 *      List<T> lighter than(T elem) - method that returns a list of elements that weigh less than elem
 *      List<T> between(T a, T b) - retuns a list that contains all elements that weigh more than a and less than b
 *      Method compare - that will compare the weight of two containers and return 0 if they have the same weight,
 *      -1 if the container that is an argument weighs more, and 1 otherwise.
 *      The weight of a container is calculated as the sum of all elements in the container.
 */


interface Weightable extends Comparable<Weightable>{
    double getWeight();

    @Override
    default int compareTo(Weightable o){
        return Double.compare(this.getWeight(),o.getWeight());
    }
}

class WeightableDouble implements Weightable {
    double weight;

    public WeightableDouble(double weight) {
        this.weight = weight;
    }

    @Override
    public double getWeight() {
        return weight;
    }
}

class WeightableString implements Weightable {
    String word;

    public WeightableString(String word) {
        this.word = word;
    }

    @Override
    public double getWeight() {
        return word.length();
    }
}

class Container<T extends Weightable>{
    List<T> elems;

    public Container() {
        elems = new ArrayList<>();
    }

    public void addElement(T elem) {
        elems.add(elem);
    }

    public List<T> lighterThan(T elem) {
        return elems.stream()
                .filter(element -> element.compareTo(elem) < 0)
                .collect(Collectors.toList());
    }

    public List<T> between(T a, T b) {
        return elems.stream()
                .filter(element -> element.compareTo(a) > 0 && element.compareTo(b) < 0)
                .collect(Collectors.toList());
    }

    public double getTotalWeight(){
        return elems.stream().mapToDouble(Weightable::getWeight).sum();
    }

    public int compare(Container<? extends Weightable> container2) {
        return Double.compare(this.getTotalWeight(),container2.getTotalWeight());
    }
}

public class ContainerTester {
    public static void main(String[] args) {
        Container<WeightableDouble> container1 = new Container();
        Container<WeightableDouble> container2 = new Container();
        Container<WeightableString> container3 = new Container();
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int m = sc.nextInt();
        int p = sc.nextInt();
        double a = sc.nextDouble();
        double b = sc.nextDouble();
        WeightableDouble wa = new WeightableDouble(a);
        WeightableDouble wb = new WeightableDouble(b);
        for (int i = 0; i < n; i++) {
            double weight = sc.nextDouble();
            container1.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < m; i++) {
            double weight = sc.nextDouble();
            container2.addElement(new WeightableDouble(weight));
        }
        for (int i = 0; i < p; i++) {
            String s = sc.next();
            container3.addElement(new WeightableString(s));
        }
        List<WeightableDouble> resultSmaller = container1.lighterThan(wa);
        List<WeightableDouble> resultBetween = container1.between(wa, wb);
        System.out.println("Lighter than " + wa.getWeight() + ":");
        for (WeightableDouble wd : resultSmaller) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Between " + wa.getWeight() + " and " + wb.getWeight() + ":");
        for (WeightableDouble wd : resultBetween) {
            System.out.println(wd.getWeight());
        }
        System.out.println("Comparison: ");
        System.out.println(container1.compare(container2));
        System.out.println(container1.compare(container3));
    }
}
