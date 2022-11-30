package vezbi.audReRun.aud3.bank;

public abstract class Account {
    private String accountOwner;
    private int id; //can be long, needs to be sequential (+1...)
    private static int idSeed = 10000;
    private double currentAmount; //balance
    private AccountType accountType;

    public Account(String accountOwner, double currentAmount) {
        this.accountOwner = accountOwner;
        this.currentAmount = currentAmount;
        this.id = idSeed++;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount){
        this.currentAmount += amount;
    }

    public void withdrawAmount(double amount) throws CannotWithdrawMoneyException {
        if(currentAmount >= amount)
            throw new CannotWithdrawMoneyException(currentAmount, amount);
        this.currentAmount -= amount;
    }

    public abstract AccountType getAccountType();

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}
