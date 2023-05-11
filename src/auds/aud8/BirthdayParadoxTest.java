package auds.aud8;

import java.util.HashSet;
import java.util.Random;

class BirthdayParadox{
    int brPeople;
    static int TRIALS = 5000;

    public BirthdayParadox(int brPeople) {
        this.brPeople = brPeople;
    }

    public void conductExperiment() {
        for(int i=2;i<=brPeople;i++){
            System.out.println(String.format("%d -> %.10f", i, runSimulation(i)));
        }
    }

    private float runSimulation(int people) {
        int br = 0;
        Random random = new Random();
        for(int i=0;i<TRIALS;i++){
            if(runTrial(people,random)){
                br++;
            }
        }
        return (float)br/TRIALS;
    }

    private boolean runTrial(int people, Random random) {
        HashSet<Integer> birthdays = new HashSet<>();
        for(int i=0; i<people; i++){
            int birthday = random.nextInt(365)+1;
            if(birthdays.contains(birthday)){
                return true;
            } else birthdays.add(birthday);
        }
        return false;
    }
}

public class BirthdayParadoxTest {
    public static void main(String[] args) {
        BirthdayParadox bp = new BirthdayParadox(50);
        bp.conductExperiment();
    }
}
