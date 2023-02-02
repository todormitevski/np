package labs.Intro.bank;

public class FlatPercentProvisionTransaction extends Transaction{
    private final int percent;

    public FlatPercentProvisionTransaction(long fromId, long toId, String amount, int centsPerDolar) {
        super(fromId, toId, "FlatPercent", amount);
        this.percent = centsPerDolar;
    }

    @Override
    public String provision() {
        long amount = Bank.toNumber(this.amount);
        amount = amount / 100;
        long provision = percent * amount;
        return Bank.toString(provision);
    }

    public int getPercent() {
        return percent;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}
