package auds.aud3.bank;

public class PlatinumCheckingAccount extends InterestCheckingAccount{
    public PlatinumCheckingAccount(String accountOwner, int id, double currentAmount) {
        super(accountOwner, id, currentAmount);
    }

    @Override
    public void addInterest() {
        addAmount(getCurrentAmount()*INTEREST_RATE * 2);
    }
}
