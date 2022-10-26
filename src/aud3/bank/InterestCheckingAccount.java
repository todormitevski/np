package aud3.bank;

public class InterestCheckingAccount extends Account implements InterestBearingAccount{
    public static final double INTEREST_RATE=0.03;
    public InterestCheckingAccount(String accountOwner, int id, double currentAmount) {
        super(accountOwner, id, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.INTEREST;
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount()*INTEREST_RATE);
    }
}
