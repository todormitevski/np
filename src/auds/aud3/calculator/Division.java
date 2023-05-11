package auds.aud3.calculator;

public class Division implements Strategy{
    @Override
    public double calculate(double numOne, double numTwo) {
        return numOne / numTwo;
    }
}
