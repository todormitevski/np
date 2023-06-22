package auds_re.aud3;

import java.util.Scanner;
import java.util.function.Predicate;

class InvalidOperationException extends Exception{
    public InvalidOperationException() {
        super("Operation is invalid!");
    }
}

interface Strategy{ //strategy design pattern
    double calculate(double num1, double num2);
}

class Addition implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1 + num2;
    }
}

class Subtraction implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1 - num2;
    }
}

class Multiplication implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        return num1 * num2;
    }
}

class Division implements Strategy{

    @Override
    public double calculate(double num1, double num2) {
        if(num2 != 0) //avoids division with 0 exception
            return num1 / num2;
        else return num1;
    }
}

class Calculator{
    double result;
    Strategy strategy;

    public Calculator() {
        result = 0.0;
    }

    public String execute(char operation, double value) throws InvalidOperationException {
        switch (operation){
            case '+': strategy = new Addition(); break;
            case '-': strategy = new Subtraction(); break;
            case '*': strategy = new Multiplication(); break;
            case '/': strategy = new Division(); break;
            default: throw new InvalidOperationException();
        }
        result = strategy.calculate(result,value);
        return String.format("result %c %.2f = %.2f",operation,value,result);
    }

    public double getResult() {
        return result;
    }

    public String init(){
        return String.format(" result = %.2f", result);
    }

    @Override
    public String toString() {
        return String.format("updated result = %.2f", result);
    }
}

public class CalculatorTest {
    public static char getCharLower(String line){
        if(line.trim().length() > 0){
            return Character.toLowerCase(line.charAt(0));
        } else return '?';
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while(true){
            Calculator calculator = new Calculator();
            System.out.println(calculator.init());

            while(true){
                String line = scanner.nextLine();
                char choice = getCharLower(line);
                if(choice == 'r'){
                    System.out.println(String.format("final result - %f", calculator.getResult()));
                    break;
                }

                String[] parts = line.split("\\s+");
                char operator = parts[0].charAt(0);
                double value = Double.parseDouble(parts[1]);
                try {
                    String result = calculator.execute(operator,value);
                    System.out.println(result);
                    System.out.println(calculator);
                } catch (InvalidOperationException e) {
                    System.out.println(e.getMessage());
                }
            }

            System.out.println("(Y/N)");
            String line = scanner.nextLine();
            char choice = getCharLower(line);
            if(choice == 'n') break;
        }
    }
}
