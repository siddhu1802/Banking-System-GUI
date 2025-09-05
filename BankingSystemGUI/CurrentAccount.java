class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(String accountNumber, double overdraftLimit) {
        super(accountNumber);
        this.overdraftLimit = overdraftLimit;
    }

    @Override
public void withdraw(double amount) {
    if (amount > 0 && getBalance() + overdraftLimit >= amount) {
        double newBalance = getBalance() - amount;

        // Adjust overdraft limit if necessary
        if (newBalance < 0) {
            overdraftLimit += newBalance; // Decrease overdraft limit by the overdraft used
        }

        // Use reflection to update the private balance field in the parent Account class
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
        System.out.println("Insufficient funds including overdraft limit.");
    }
}

    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public void accountDetails() {
        System.out.println("Current Account Number: " + getAccountNumber());
        System.out.println("Balance: " + getBalance());
        System.out.println("Overdraft Limit: " + overdraftLimit);
    }
    @Override
    public String getAccountType() {
        return "Current Account";
    }
    
}
