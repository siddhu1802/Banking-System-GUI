import java.util.ArrayList;
import java.util.List;

abstract class Account {
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactions;

    public Account(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void deposit(double amount) {
        if (amount > 0) {
            balance += amount;
            Transaction transaction = new Transaction(accountNumber, "Deposit", amount);
            transactions.add(transaction);
            System.out.println("Deposit successful: " + amount);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance -= amount;
            Transaction transaction = new Transaction(accountNumber, "Withdrawal", amount);
            transactions.add(transaction);
            System.out.println("Withdrawal successful: " + amount);
        } else {
            System.out.println("Insufficient balance or invalid amount.");
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }
    public abstract String getAccountType();


    public abstract void accountDetails(); // Method for displaying specific account details in subclasses
}
