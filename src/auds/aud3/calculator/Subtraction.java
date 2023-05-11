package auds.aud3.calculator;

public class Subtraction implements Strategy{
    @Override
    public double calculate(double numOne, double numTwo) {
        return numOne - numTwo;
    }
}
