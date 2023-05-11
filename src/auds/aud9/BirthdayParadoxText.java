package auds.aud9;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.IntStream;

class Trial{
    int people;
    Set<Integer> birthdaysSet;
    static Random RANDOM = new Random();

    public Trial(int people) {
        this.people = people;
        birthdaysSet = new HashSet<>();
    }

    boolean run(){
        for(int i=0;i<people;i++){
            int birthday = RANDOM.nextInt(365)+1; //from 0 to 365
            if(birthdaysSet.contains(birthday))
                return true;
            else
                birthdaysSet.add(birthday);
        }
        return false;
    }
}

class Experiment{
    int people;
    static int TRIALS = 5000;

    public Experiment(int people) {
        this.people = people;
    }

    public double run(){
        int count = (int)IntStream.range(0,TRIALS)
                .mapToObj(i -> new Trial(people))
                .map(Trial::run)
                .filter(b -> b)
                .count();

//        int count = 0;
//        for(int i=0;i<TRIALS;i++){
//            Trial trial = new Trial(people);
//            if(trial.run())
//                ++count;
//        }
        return (float)count / TRIALS;
    }

    @Override
    public String toString() {
        return String.format("For %d people, the probability of two birthdays is about %.5f\n", people, run());
    }
}

public class BirthdayParadoxText {
    public static void main(String[] args) {
        for(int i=2;i<=50;i++){
            Experiment experiment = new Experiment(i);
            System.out.println(experiment);
        }
    }
}
