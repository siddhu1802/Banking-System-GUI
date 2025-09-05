import java.util.Calendar;
import java.util.Date;


class FixedAccount extends Account {
    private final double interestRate; // Updated to accept double values
    private final double minimumBalance; // Updated to accept double values
    private final int termInYears;
    private Date maturityDate;

    public FixedAccount(String accountNumber, double interestRate, double minimumBalance, int termInYears) {
        super(accountNumber);
        this.interestRate = interestRate;
        this.minimumBalance = minimumBalance;
        this.termInYears = termInYears;
        setMaturityDate(); // Set maturity date on creation
    }

    private void setMaturityDate() {
        // Set the maturity date based on the term of the fixed account
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, termInYears);
        this.maturityDate = calendar.getTime();
    }

    @Override
    public void withdraw(double amount) {
        Date currentDate = new Date();
        if (currentDate.after(maturityDate)) {
            if (amount > 0 && getBalance() - amount >= minimumBalance) {
                // Update the balance using reflection
                double newBalance = getBalance() - amount;

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
        } else {
            System.out.println("Withdrawal not allowed before maturity date: " + maturityDate);
        }
    }

    @Override
    public void accountDetails() {
        System.out.println("Fixed Account Number: " + getAccountNumber());
        System.out.println("Balance: " + getBalance());
        System.out.println("Interest Rate: " + interestRate + "%");
        System.out.println("Minimum Balance: " + minimumBalance);
        System.out.println("Term (Years): " + termInYears);
        System.out.println("Maturity Date: " + maturityDate);
    }

    public int getTermInYears() {
        return termInYears;
    }

    public Date getMaturityDate() {
        return maturityDate;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public double getMinimumBalance() {
        return minimumBalance;
    }

    @Override
    public String getAccountType() {
        return "Fixed Account";
    }

    public void addInterest() {
        double interest = getBalance() * (interestRate / 100);
        Transaction transaction = new Transaction(getAccountNumber(), "Interest", interest);
        getTransactions().add(transaction);
        System.out.println("Interest added: " + interest);
    }
}
