package vezbi.audReRun.aud3.calculator;

public class UnknownOperatorException extends Exception{
    public UnknownOperatorException(char operator) {
        super(String.format("THis operator %c is not valid", operator));
    }
}
