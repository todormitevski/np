package labs.Intro.bank;

public class FlatAmountProvisionTransaction extends Transaction{
    private final String flatAmount;

    public FlatAmountProvisionTransaction(long fromId, long toId, String amount, String flatProvision) {
        super(fromId, toId, "FlatAmount", amount);
        this.flatAmount = flatProvision;
    }

    @Override
    public String provision() {
        return flatAmount;
    }

    public String getFlatAmount() {
        return flatAmount;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
