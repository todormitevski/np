package auds.aud3.bank;

public class CannotWithdrawMoneyException extends Exception{
    public CannotWithdrawMoneyException(double currentAmount, double amount) {
        super(String.format("Your current amount is: %.2f, and you cannot withdraw this amount: %.2f",
                currentAmount,amount));
    }
}
