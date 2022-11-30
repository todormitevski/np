package vezbi.audReRun.aud3.bank;

public class NonInterestCheckingAccount extends Account{
    public NonInterestCheckingAccount(String accountOwner, double currentAmount) {
        super(accountOwner, currentAmount);
    }

    @Override
    public AccountType getAccountType() {
        return AccountType.NONINTEREST;
    }
}
