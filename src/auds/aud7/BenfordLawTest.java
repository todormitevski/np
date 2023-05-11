package auds.aud7;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

class Counter{
    int countingArray[];

    public Counter(){
        countingArray = new int[10];
    }

    public void addToCounter(int digit){
        countingArray[digit]++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int sum = 0;
        for(int i=1;i<countingArray.length;i++){
            sum+=countingArray[i];
        }

        for(int i=1;i<countingArray.length;i++){
            sb.append(String.format("%d: %.2f%%\n", i, (float)countingArray[i]/sum * 100.0));
        }
        return sb.toString();
    }
}

class BenfordLawExperiment{
    List<Integer> numbers;
    Counter counter;

    public BenfordLawExperiment() {
        this.numbers = new ArrayList<>();
        this.counter = new Counter();
    }

    public void readData(InputStream inputStream) {
        // scanner way
//        Scanner sc = new Scanner(inputStream);
//        while(sc.hasNext()){
//            int number = sc.nextInt();
//            numbers.add(number);
//        }

        // stream way
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        numbers = br.lines()
                .filter(line -> !line.equals(""))
                .map((Integer::parseInt))
                .collect(Collectors.toList());
    }

    public void conductExperiment() {
        numbers.stream()
                .map(this::getFirstDigit)
                .forEach(firstDigit -> counter.addToCounter(firstDigit));
    }

    private int getFirstDigit(Integer each) {
        while(each >= 10) {
            each /= 10;
        }
        return each;
    }

    @Override
    public String toString() {
        return counter.toString();
    }
}

public class BenfordLawTest {
    public static void main(String[] args) {
        BenfordLawExperiment experiment = new BenfordLawExperiment();
        try {
            experiment.readData(new FileInputStream("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\auds.aud7\\data\\librarybooks.txt"));
            experiment.conductExperiment();
            System.out.println(experiment);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
