import java.util.Date;

class Transaction {
    private final String accountNumber;
    private final String type; // "Deposit" or "Withdrawal"
    private double amount;
    private Date date;
    private String description;

    public Transaction(String accountNumber, String type, double amount) {
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.date = new Date(); // Sets the current date and time
        this.description = description;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getType() {
        return this.type;
    }

    public double getAmount() {
        return this.amount;
    }

    public Date getDate() {
        return this.date;
    }

    @Override
    public String toString() {
        return type + " of " + amount + " on " + date;
    }
    public String getDescription() {
        return this.description;
    }
}
