class SavingsAccount extends Account {
    private final double interestRate;
    private final double minimumBalance;

    public SavingsAccount(String accountNumber, double interestRate, double minimumBalance) {
        super(accountNumber);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
    }

    @Override
public void withdraw(double amount) {
    if (amount > 0 && getBalance() - amount >= minimumBalance) {
        // Update the balance
        double newBalance = getBalance() - amount;

        // Use reflection to access private balance field in the parent Account class
        try {
            java.lang.reflect.Field balanceField = Account.class.getDeclaredField("balance");
            balanceField.setAccessible(true);
            balanceField.set(this, newBalance); // Update the balance
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Failed to update balance.");
            return;
        }

        // Record the transaction
        Transaction transaction = new Transaction(getAccountNumber(), "Withdrawal", amount);
        getTransactions().add(transaction);

        System.out.println("Withdrawal successful: " + amount);
    } else {
        System.out.println("Insufficient funds or below minimum balance requirement.");
    }
}


    public void addInterest() {
        double interest = getBalance() * (interestRate / 100);
        Transaction transaction = new Transaction(getAccountNumber(), "Interest", interest);
        getTransactions().add(transaction);
        System.out.println("Interest added: " + interest);
    }

    @Override
    public void accountDetails() {
        System.out.println("Savings Account Number: " + getAccountNumber());
        System.out.println("Balance: " + getBalance());
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Minimum Balance: " + minimumBalance);
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }
    @Override
    public String getAccountType() {
        return "Savings Account";
    }
}
