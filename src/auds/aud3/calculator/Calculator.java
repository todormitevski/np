package auds.aud3.calculator;

public class Calculator {
    private double result;
    private Strategy strategy;

    public Calculator() {
        this.result = 0.0;
    }

    public String execute(char operation, double value) throws UnknownOperatorException {
        if(operation=='+'){
            strategy = new Addition();
        }
        else if(operation=='-'){
            strategy = new Subtraction();
        }
        else if(operation=='*'){
            strategy = new Multiplication();
        }
        else if(operation=='/'){
            strategy = new Division();
        }
        else{
            throw new UnknownOperatorException(operation);
        }

        result = strategy.calculate(result, value);
        return String.format("result %c %.2f = %.2f", operation, value, result);
    }

    public double getResult() {
        return result;
    }

    public String init(){ //initialization print
        return String.format("result = %.2f", result);
    }

    @Override
    public String toString() {
        return String.format("updated result = %.2f", result);
    }
}
