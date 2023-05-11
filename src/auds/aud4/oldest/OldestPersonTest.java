package auds.aud4.oldest;

import java.io.*;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class OldestPersonTest {
    public static List<Person> readData(InputStream inputStream){

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        return bufferedReader.lines()
                .map(line -> new Person(line))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        File file = new File("E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\auds.aud4\\files\\skt.txt");

        try {

            List<Person> skt = readData(new FileInputStream(file));
            Collections.sort(skt);
            System.out.println(skt.get(skt.size() - 1));

            //second way
            if(skt.stream().max(Comparator.naturalOrder()).isPresent()){
                System.out.println(skt.stream().max(Comparator.naturalOrder()).get());
            }

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
