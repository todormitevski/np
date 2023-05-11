package auds.aud3.calculator;

public class Addition implements Strategy{

    @Override
    public double calculate(double numOne, double numTwo) {
        return numOne + numTwo;
    }
}
