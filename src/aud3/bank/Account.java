package aud3.bank;

public abstract class Account {
    private String accountOwner;
    private int id; //long more useful, locally stored
    private static int idSeed=10000; //stored vo nivo na klasa
    private double currentAmount; //balance
    private AccountType accountType;

    public Account(String accountOwner, int id, double currentAmount) {
        this.accountOwner = accountOwner;
        this.currentAmount = currentAmount;
        this.id=idSeed;
        idSeed++;
    }

    public double getCurrentAmount() {
        return currentAmount;
    }

    public void addAmount(double amount){
        this.currentAmount+=amount;
    }

    public void withdrawAmount(double amount) throws CannotWithdrawMoneyException{
        if(currentAmount<amount)
            throw new CannotWithdrawMoneyException(currentAmount,amount); //breaks out of method
        currentAmount-=amount;
    }

    public abstract AccountType getAccountType();

    @Override
    public String toString() {
        return String.format("%d: %.2f", id, currentAmount);
    }
}
