package auds.aud11;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Searcher extends Thread{
    int start;
    int end;
    int min;
    int max;

    public Searcher(int start, int end) {
        this.start = start;
        this.end = end;
    }

    public int getMin() {
        return min;
    }

    public int getMax() {
        return max;
    }

    @Override
    public void run() {
        min = max = ArrayParallelization.ARRAY[start];
        for(int i=start+1;i<end;i++){
            if(ArrayParallelization.ARRAY[i] < min)
                min = ArrayParallelization.ARRAY[i];
            if(ArrayParallelization.ARRAY[i] > max)
                max = ArrayParallelization.ARRAY[i];
        }
    }

    @Override
    public String toString() {
        return "Searcher{" +
                "start=" + start +
                ", end=" + end +
                ", min=" + min +
                ", max=" + max +
                '}';
    }
}

public class ArrayParallelization {
    public static int ARRAY_SIZE = 10000000;
    public static int THREAD_COUNT = 100;
    public static int[] ARRAY = new int[ARRAY_SIZE];
    public static Random rand = new Random();

    public static void main(String[] args) {
        for(int i=0;i<ARRAY_SIZE;i++){
            ARRAY[i] = rand.nextInt(51); //0 - 100
        }

        ARRAY[150000] = -5; //global min
        ARRAY[420000] = 105; //global max

        List<Searcher> searchers = new ArrayList<>();
        for(int i=0;i<THREAD_COUNT;i++){
            int start = i * ARRAY_SIZE / THREAD_COUNT;
            int end = start + ARRAY_SIZE / THREAD_COUNT;
            searchers.add(new Searcher(start,end));
        }
        searchers.forEach(Thread::start);
        searchers.forEach(searcher -> {
            try {
                searcher.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });
        //searchers.forEach(System.out::println);
        int globalMin = searchers.stream()
                .mapToInt(Searcher::getMin)
                .min()
                .getAsInt();
        int globalMax = searchers.stream()
                .mapToInt(Searcher::getMax)
                .max()
                .getAsInt();
        System.out.println(globalMin);
        System.out.println(globalMax);
    }
}
