package aud3.bank;

public class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String accountOwner, int id, double currentAmount) {
        super(accountOwner, id, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NON_INTEREST;
    }
}
