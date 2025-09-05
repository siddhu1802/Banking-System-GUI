import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


class Customer extends User {
    private List<Account> accounts;
    private static Set<String> existingAccountNumbers = new HashSet<>();
    double min = 0;
    double initialDeposit = 0;
    private List<Loan> loans = new ArrayList<>();

    

    public Customer(String username, String password) {
        super(username, password);
        this.accounts = new ArrayList<>();
    }
    public String getUsername() {
        return this.username;
    }
    
    public void createSavingsAccount(String accountNumber, double interestRate,double minimumBalance) {
        if (!existingAccountNumbers.add(accountNumber)) {
            System.out.println("Account number already exists. Choose a different number.");
            return;
        }
        accounts.add(new SavingsAccount(accountNumber, interestRate,minimumBalance));
        System.out.println("Savings account created with account number: " + accountNumber);
    }

    public void createFixedAccount(String accountNumber, double interestRate, double minimumBalance, int termInYears) {
        if (!existingAccountNumbers.add(accountNumber)) {
            System.out.println("Account number already exists. Choose a different number.");
            return;
        }
        accounts.add(new FixedAccount(accountNumber,interestRate, minimumBalance, termInYears));
        System.out.println("Fixed account created with account number: " + accountNumber);
    }

    public void createCurrentAccount(String accountNumber, double overdraftLimit,double initialDeposit) {
        if (!existingAccountNumbers.add(accountNumber)) {
            System.out.println("Account number already exists. Choose a different number.");
            return;
        }
        accounts.add(new CurrentAccount(accountNumber, overdraftLimit));
        System.out.println("Current account created with account number: " + accountNumber);
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void viewBalance(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                System.out.println("Balance for account " + accountNumber + ": " + account.getBalance());
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void deposit(String accountNumber, double amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.deposit(amount);
                System.out.println("Deposited " + amount + " to account " + accountNumber);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void withdraw(String accountNumber, double amount) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                account.withdraw(amount);
                return;
            }
        }
        System.out.println("Account not found.");
    }

    public void viewTransactionHistory(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                System.out.println("Transaction history for account " + accountNumber + ":");
                for (Transaction transaction : account.getTransactions()) {
                    System.out.println(transaction);
                }
                return;
            }
        }
        System.out.println("Account not found.");
    }
    public boolean closeAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                accounts.remove(account);
                System.out.println("Account " + accountNumber + " has been successfully closed.");
                return true;
            }
        }
        System.out.println("Account not found.");
        return false;
    }
    public String viewAccountDetails() {
        if (accounts.isEmpty()) {
            return "No accounts found.";
        } else {
            StringBuilder accountDetails = new StringBuilder("Account Details:\n");
            for (Account account : accounts) {
                accountDetails.append("-------------------------\n")
                              .append("Account Number: ").append(account.getAccountNumber()).append("\n")
                              .append("Account Type: ").append(account.getAccountType()).append("\n")
                              .append("Balance: ").append(account.getBalance()).append("\n");
            }
            accountDetails.append("-------------------------");
            return accountDetails.toString();
        }
    }
    

    public Account getAccount(String accountNumber) {
        for (Account account : accounts) {
            if (account.getAccountNumber().equals(accountNumber)) {
                return account;
            }
        }
        return null;  // If account is not found
    }
    public String getPassword() {
        return this.password;  // Assuming `password` is a field in the `User` class
    }
    // In Customer class
    public boolean transferLoanToAccount(String loanId) {
        // Find the loan by its ID
        Loan loan = findLoanById(loanId);  
        
        if (loan != null) {
            double loanAmount = loan.getLoanAmount();  // Get the loan amount
            
            if (accounts.isEmpty()) {
                System.out.println("No accounts available to transfer the loan to.");
                return false;  // No account to transfer to
            } else {
                Account account = accounts.get(0);
                
                // Deposit the loan amount to the account
                account.deposit(loanAmount);  
                System.out.println("Loan amount of " + loanAmount + " has been transferred to account " + account.getAccountNumber());
    
                loan.repay(loanAmount);  
                
                return true;
            }
        } else {
            System.out.println("Loan not found.");
            return false;  // Loan not found
        }
    }
    
    
// In Customer class
public void repayLoan(String loanId, double amount) {
    Loan loan = findLoanById(loanId);
    if (loan != null) {
        if (amount > 0) {
            loan.makePayment(amount);
            System.out.println("Payment of " + amount + " has been made. Remaining loan balance: " + loan.getRemainingBalance());
        } else {
            System.out.println("Invalid payment amount.");
        }
    } else {
        System.out.println("Loan not found.");
    }
}
public Loan findLoanById(String loanId) {
    for (Loan loan : loans) {
        if (loan.getLoanId().equals(loanId)) {
            return loan;
        }
    }
    return null;  // Return null if loan with the given ID is not found
}
public String viewAllLoans() {
    if (loans == null || loans.isEmpty()) {
        return "No loans available."; // Return a message when no loans exist
    }

    StringBuilder allLoansDetails = new StringBuilder("Customer Loans:\n");
    for (Loan loan : loans) {
        allLoansDetails.append(loan.viewLoanDetails()).append("\n");
    }
    return allLoansDetails.toString();
}

public double getRemainingBalance(String loanId) {
    for (Loan loan : loans) { // Assuming `loans` is a list of Loan objects associated with the customer
        if (loan.getLoanId().equals(loanId)) { // Assuming `getId()` returns the Loan ID
            return loan.getRemainingBalance(); // Assuming `getAmount()` returns the remaining loan balance
        }
    }
    return -1;
}
// Close a loan when it's fully paid off
public void closeLoan(String loanId) {
    Loan loan = findLoanById(loanId);  // Find the loan by ID
    if (loan != null) {
        loan.closeLoan();  // Close the loan (no arguments needed)
        if (loan.getRemainingBalance() == 0) {
            loans.remove(loan);  // Remove the loan from the list if it's fully paid off
            System.out.println("Loan " + loanId + " has been successfully closed.");
        } else {
            System.out.println("Loan still has a remaining balance and cannot be closed.");
        }
    } else {
        System.out.println("Loan with ID " + loanId + " not found.");
    }
}

public void makeLoanPayment(String loanId, double amount) {
    Loan loan = findLoanById(loanId);
    if (loan != null) {
        loan.makePayment(amount);
    } else {
        System.out.println("Loan with ID " + loanId + " not found.");
    }
}
 // Add a loan to the customer's loan list
 public void createLoan(String loanId, double loanAmount, double interestRate, int loanTermInYears, Date startDate) {
    Loan newLoan = new Loan(loanId, loanAmount, interestRate, loanTermInYears, startDate);
    loans.add(newLoan);
    System.out.println("Loan created with ID: " + loanId);
}
public void viewLoanDetails(String loanId) {
    Loan loan = findLoanById(loanId);
    if (loan != null) {
        loan.viewLoanDetails();
    } else {
        System.out.println("Loan with ID " + loanId + " not found.");
    }
}
public boolean hasAccount(String accountNumber) {
    for (Account account : this.getAccounts()) {    
        if (account.getAccountNumber().equals(accountNumber)) {
            return true;
        }
    }
    return false;
}


}
