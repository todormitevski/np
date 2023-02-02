package labs.Intro.arrayOfIntegers;

import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

class IntegerArray{
    private final int[] arrInts;

    public IntegerArray(int[] arrInts) {
        this.arrInts = arrInts;
    }

    public int length(){
        return arrInts.length;
    }

    public int getElementAt(int i){
        return arrInts[i];
    }

    public int sum(){
        return Arrays.stream(arrInts).sum();
    }

    public double average(){
        return ((double) sum()) / length();
    }

    public IntegerArray getSorted(){
        int[] sortedArr = Arrays.stream(arrInts).sorted().toArray();
        return new IntegerArray(sortedArr);
    }

    public IntegerArray concat(IntegerArray ia){
        int[] concatArr = new int[arrInts.length + ia.length()];
        System.arraycopy(arrInts,0,concatArr,0,arrInts.length);
        System.arraycopy(ia,0,concatArr,arrInts.length,ia.length());
        return new IntegerArray(concatArr);
    }

    @Override
    public String toString() {
        return Arrays.toString(arrInts);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IntegerArray that = (IntegerArray) o;
        return Arrays.equals(arrInts, that.arrInts);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(arrInts);
    }
}

class ArrayReader{
    public static IntegerArray readIntegerArray(InputStream input) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(input));
        int n = Integer.parseInt(br.readLine());
        String line = br.readLine();
        String[] parts = line.split("\\s+");
        int[] arr = new int[n];
        for(int i=0;i<n;i++){
            arr[i] = Integer.parseInt(parts[i]);
        }
        br.close();
        return new IntegerArray(arr);
    }
}

public class IntegerArrayTester {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) throws IOException {
        IntegerArray read = ArrayReader.readIntegerArray(in);
        System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }
}
