package auds.aud11;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

//class ThreadClass extends Thread {
//
//    int number;
//
//    public ThreadClass(int number) {
//        this.number = number;
//    }
//
//    @Override
//    public void run() {
//        System.out.println(number);
//    }
//}

public class ThreadTest {
    public static void main(String[] args) {
        List<Thread> threads = IntStream.range(1,101)
                .mapToObj(i -> new Thread(() -> System.out.println(i)))
                .collect(Collectors.toList());
        threads.forEach(Thread::start);
        threads.forEach(thread ->{
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());
            }
        });

    }
}

