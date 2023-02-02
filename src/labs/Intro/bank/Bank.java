package labs.Intro.bank;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class Bank {
    private String name;
    private Account[] accounts;
    private String totalTransfers;
    private String totalProvision;

    public Bank(String name, Account[] accounts) {
        this.name = name;
        this.accounts = Arrays.copyOf(accounts,accounts.length);
        this.totalTransfers = "0.00$";
        this.totalProvision = "0.00$";
    }

    public boolean makeTransaction(Transaction transaction){
        // врши проверка дали корисникот ги има потребните средства на
        // сметка и дали и двете сметки на кои се однесува трансакцијата
        // се нависитина во банката и ако и двата услови се исполнето ја
        // извршува трансакцијата и враќа true, во спротивно враќа false

        Optional<Account> from = findAccount(transaction.getFromId());
        Optional<Account> to = findAccount(transaction.getToId());

        if(from.isPresent() && to.isPresent()) {

            Account fromAccount = from.get();
            Account toAccount = to.get();

            long fromBalance = toNumber(fromAccount.getBalance());
            long toBalance = toNumber(toAccount.getBalance());
            long amount = toNumber(transaction.getAmount());
            long provision = toNumber(transaction.provision());

            if (provision + amount <= fromBalance) {
                if (fromAccount.getId() == toAccount.getId()) {
                    fromBalance = toBalance = (fromBalance - provision);
                } else {
                    fromBalance -= provision + amount;
                    toBalance += amount;
                }
                fromAccount.setBalance(toString(fromBalance));
                toAccount.setBalance(toString(toBalance));
                updateTotals(amount, provision);
                return true;
            }
        }
        return false;
    }

    void updateTotals(long amount, long provision){
        long totalTransfers = toNumber(this.totalTransfers);
        long totalProvision = toNumber(this.totalProvision);
        totalTransfers += amount;
        totalProvision += provision;
        this.totalTransfers = toString(totalTransfers);
        this.totalProvision = toString(totalProvision);
    }

    Optional<Account> findAccount(long id){
        return Arrays.stream(accounts)
                .filter(each -> each.getId() == id)
                .findAny();
    }

    public static long toNumber(String amount){
        String num = amount.replace(".", "").replace("$", "");
        return Long.parseLong(num);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bank bank = (Bank) o;
        return Objects.equals(name, bank.name) && Arrays.equals(accounts, bank.accounts) && Objects.equals(totalTransfers, bank.totalTransfers) && Objects.equals(totalProvision, bank.totalProvision);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, totalTransfers, totalProvision);
        result = 31 * result + Arrays.hashCode(accounts);
        return result;
    }

    public static String toString(long amount){
        return String.format("%.2f$", amount / 100.);
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("Name: ");
        result.append(name);
        result.append("\n\n");
        Arrays.stream(accounts)
                .forEach(each -> result.append(each.toString()));
        return result.toString();
    }

    public String totalTransfers(){
        return totalTransfers;
    }

    public String totalProvision(){
        return totalProvision;
    }

    public Account[] getAccounts() {
        return accounts;
    }
}
