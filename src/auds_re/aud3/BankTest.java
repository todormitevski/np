//package auds_re.aud3;
//
//import java.util.ArrayList;
//import java.util.List;
//
//class CouldNotAddAccountException extends Exception{
//    public CouldNotAddAccountException(String message) {
//        super(message);
//    }
//}
//
//class CouldNotWithdrawException extends Exception{
//    public CouldNotWithdrawException(String message) {
//        super(message);
//    }
//}
//
//enum AccountType{
//    INTEREST,
//    NON_INTEREST
//}
//
//interface InterestBearingAccount{
//    void addInterest();
//}
//
//abstract class Account{
//    String username;
//    int id; //local
//    static int idSeed = 1; //global, increases for all
//    double balance;
//    AccountType type;
//
//    public Account(String username, double balance) {
//        this.username = username;
//        this.balance = balance;
//        this.id = idSeed++;
//    }
//
//    public double getBalance() {
//        return balance;
//    }
//
//    public void addAmount(double amount){
//        this.balance += amount;
//    }
//
//    public void withdrawAmount(double amount) throws CouldNotWithdrawException {
//        if(amount > balance)
//            throw new CouldNotWithdrawException("Invalid withdraw amount!");
//        balance -= amount;
//    }
//
//    public abstract AccountType getAccountType();
//
//    @Override
//    public String toString() {
//        return String.format("%d: %.2f", id, balance);
//    }
//}
//
//class NonInterestCheckingAccount extends Account{
//
//    public NonInterestCheckingAccount(String username, double balance) {
//        super(username, balance);
//    }
//
//    @Override
//    public AccountType getAccountType() {
//        return AccountType.NON_INTEREST;
//    }
//}
//
//class InterestCheckingAccount extends Account implements InterestBearingAccount{
//    public static final double INTEREST_RATE = 0.03;
//
//    public InterestCheckingAccount(String username, double balance) {
//        super(username, balance);
//    }
//
//    @Override
//    public AccountType getAccountType() {
//        return AccountType.INTEREST;
//    }
//
//    @Override
//    public void addInterest() {
//        addAmount(getBalance() * INTEREST_RATE);
//    }
//}
//
//class PlatinumCheckingAccount extends InterestCheckingAccount{
//
//    public PlatinumCheckingAccount(String username, double balance) {
//        super(username, balance);
//    }
//
//    @Override
//    public void addInterest(){
//        addAmount(getBalance() * INTEREST_RATE * 2);
//    }
//}
//
//class Bank{
//    public List<Account> accounts;
//    int maxAccounts;
//
//    public Bank(int maxAccounts) {
//        this.maxAccounts = maxAccounts;
//        this.accounts = new ArrayList<>();
//    }
//
//    public void addAccount(Account account) throws CouldNotAddAccountException {
//        if(accounts.size() + 1 > maxAccounts)
//            throw new CouldNotAddAccountException("Accounts amount exceeding maximum!");
//        accounts.add(account);
//    }
//
//    public double totalAssets(){
//        double total = 0.0;
//        for(Account account : accounts){
//            total += account.getBalance();
//        }
//        return total;
//    }
//
//    public void addInterestToAllAccounts(){
//        for(Account account : accounts){
//            if(account.getAccountType().equals(AccountType.INTEREST)){
//
//                InterestBearingAccount ibc = (InterestBearingAccount) account;
//                ibc.addInterest();
//            }
//        }
//    }
//}
//
//public class Aud3BankTest {
//    public static void main(String[] args) {
//        //...
//    }
//}
