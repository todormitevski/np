package aud3.calculator;

import java.util.Scanner;

public class CalculatorTest {
    public static char getCharLower(String line){
        if(line.trim().length()>0){ //.trim() removes spaces
            return Character.toLowerCase(line.charAt(0));
        } else return '?';
    }

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        while(true){
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while(true){
                String line = input.nextLine();
                char choice = getCharLower(line);
                if(choice == 'r'){
                    System.out.println(String.format("final result = %f", calculator.getResult()));
                    break;
                }

                String[] parts = line.split("\\s+");
                char operator = parts[0].charAt(0); //we know there is only one char in the string
                double value = Double.parseDouble(parts[1]); //casting (parsing) from string into double

                //exceptions are thrown and caught in two different methods, 99% of the time
                try {
                    String result = calculator.execute(operator, value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (UnknownOperatorException e) { //if not caught in main, program falls
                    System.out.println(e.getMessage());
                    //e.printStackTrace();
                    //throw new RuntimeException(e);
                }
            }

            System.out.println("(Y/N)");
            String line = input.nextLine();
            char choice = getCharLower(line);
            if(choice == 'n') break;
        }
    }
}
