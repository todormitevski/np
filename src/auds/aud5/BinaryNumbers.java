package auds.aud5;

import java.io.*;
import java.util.Random;

public class BinaryNumbers {
    public static final String FILE_NAME = "E:\\IntelliJ IDEA 2022.2.2\\INTELLIJ PROGRAMS\\np\\src\\auds.aud5\\numbers.dat";

    private static void generateFile(int n){
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            Random random = new Random();

            //moze i so streams

            for(int i=0;i<n;i++){
                int nextRandom = random.nextInt(1000);
                objectOutputStream.writeInt(nextRandom);
            }
            objectOutputStream.flush(); //needs flush at end
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static double average(){
        int count = 0;
        double sum = 0;

        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_NAME));
            try{
                while(true){
                    int number = objectInputStream.readInt();
                    sum += number;
                    count++;
                }
            } catch (EOFException e){
                System.out.println("End of file was reached.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sum / count;
    }

    public static void main(String[] args) {
        generateFile(1000);
        System.out.println("Average: " + average());
    }
}
