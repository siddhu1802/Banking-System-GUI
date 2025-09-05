import java.util.Date;
import java.util.Scanner;
import java.util.regex.*;

class CustomerAI {
    private Customer customer; // Reference to the logged-in customer
    private Scanner scanner; // Scanner object shared across methods

    // Constructor: AI is instantiated with the logged-in customer
    public CustomerAI(Customer customer) {
        this.customer = customer;
        this.scanner = new Scanner(System.in); // Initialize Scanner here
    }

    // Handle the query from the user
    public void handleQuery(String query) {
        if (Pattern.compile(".*(balance|amount|how much|available|in account|current balance|funds|money|how much money|how much is|what is|total balance|what's the balance|account balance|account money|remaining balance|account funds).*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            System.out.print("Please provide account number: ");
            String accountNumber = scanner.nextLine();
            viewBalance(accountNumber);

        // Handle deposit using various patterns
        } else if (Pattern.compile(".*\\b(deposit|add|put|transfer|inject|credit|fund|deposit funds|place|insert)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            System.out.print("Please provide account number: ");
            String accountNumber = scanner.nextLine();
        
            System.out.print("Enter amount to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            deposit(accountNumber, amount);
        }

        // Handle withdrawal using various patterns
        else if (Pattern.compile(".*\\b(withdraw|take|remove|deduct|debit|pull|take out|draw|extract|substract|clear|decrease)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            System.out.print("Please provide account number: ");
            String accountNumber = scanner.nextLine();
        
            System.out.print("Enter amount to withdraw: ");
            double amount = scanner.nextDouble();
            scanner.nextLine(); // Consume newline
            withdraw(accountNumber, amount);
        }

        // Handle transaction history
        else if (Pattern.compile(".*\\b(transaction history|transactions|statement|txn|account statement|transaction log|transaction record|history|transaction)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            System.out.print("Please provide account number: ");
            String accountNumber = scanner.nextLine();
            viewTransactionHistory(accountNumber);
        }

        // Handle view account details
        else if (Pattern.compile(".*\\b(view account details|account info|account details|account summary|account overview|view account|account status)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            System.out.print("Would you like to view all accounts or a specific account? (all/specific): ");
            String choice = scanner.nextLine();
            if (choice.equalsIgnoreCase("all")) {
                viewAllAccountDetails();
            } else {
                System.out.print("Please provide account number: ");
                String accountNumber = scanner.nextLine();
                viewAccountDetails(accountNumber);
            }
        }

        // Handle account creation using various patterns
        else if (Pattern.compile(".*\\b(create|open|new|register|set up)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            // Check if the user mentioned an account type (savings, fixed, current)
            if (Pattern.compile(".*\\b(savings|fixed|current)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
                createAccount(query);  // Proceed with account creation based on specified type
            } else {
                // If no account type is specified, ask the user to choose one
                System.out.println("Please specify the type of account you want to create: savings, fixed, or current.");
                String accountType = scanner.nextLine().trim().toLowerCase();
                // Proceed to create the specified account type
                if (accountType.equals("savings")) {
                    createSavingsAccount();
                } else if (accountType.equals("fixed")) {
                    createFixedAccount();
                } else if (accountType.equals("current")) {
                    createCurrentAccount();
                } else {
                    System.out.println("Invalid account type. Please choose savings, fixed, or current.");
                }
            }
        }
        // Handle Create Loan
    else if (Pattern.compile(".*\\b(create|apply|new|take out|loan)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
    createLoan();
}

// Handle View Loans
else if (Pattern.compile(".*\\b(view|list|show|all)\\b.*\\b(loans)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
    viewLoans();
}

// Handle Repay Loan
else if (Pattern.compile(".*\\b(repay|pay|settle|clear)\\b.*\\b(loan)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
    repayLoan();
}

// Handle Transfer Loan to Account
else if (Pattern.compile(".*\\b(transfer|move|deposit)\\b.*\\b(loan)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
    transferLoanToAccount();
}

// Handle Close Loan
else if (Pattern.compile(".*\\b(close|finish|end|close out)\\b.*\\b(loan)\\b.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
    closeLoan();
}
 
        else {
            System.out.println("I'm sorry, I didn't understand your query.");
        }
    }

    // View balance for a specific account
    private void viewBalance(String accountNumber) {
        Account account = customer.getAccount(accountNumber); // Get account specific to the customer
        if (account != null) {
            System.out.println("Balance for account " + accountNumber + ": " + account.getBalance());
        } else {
            System.out.println("Account not found.");
        }
    }

    // Deposit into a specific account
    private void deposit(String accountNumber, double amount) {
        Account account = customer.getAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            System.out.println("Deposited " + amount + " to account " + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    // Withdraw from a specific account
    private void withdraw(String accountNumber, double amount) {
        Account account = customer.getAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            System.out.println("Withdrew " + amount + " from account " + accountNumber);
        } else {
            System.out.println("Account not found.");
        }
    }

    // View transaction history for a specific account
    private void viewTransactionHistory(String accountNumber) {
        Account account = customer.getAccount(accountNumber);
        if (account != null) {
            System.out.println("Transaction history for account " + accountNumber + ":");
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction);
            }
        } else {
            System.out.println("Account not found.");
        }
    }

    // View all account details
    private void viewAllAccountDetails() {
        customer.viewAccountDetails();
    }

    // View specific account details
    private void viewAccountDetails(String accountNumber) {
        Account account = customer.getAccount(accountNumber);
        if (account != null) {
            System.out.println("-------------------------");
            System.out.println("Account Number: " + account.getAccountNumber());
            System.out.println("Account Type: " + account.getAccountType());
            System.out.println("Balance: " + account.getBalance());
            System.out.println("-------------------------");
        } else {
            System.out.println("Account not found.");
        }
    }

    // Create an account based on the query (e.g., "create savings account")
    private void createAccount(String query) {
        if (Pattern.compile(".*savings.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            createSavingsAccount();
        } else if (Pattern.compile(".*fixed.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            createFixedAccount();
        } else if (Pattern.compile(".*current.*", Pattern.CASE_INSENSITIVE).matcher(query).find()) {
            createCurrentAccount();
        } else {
            System.out.println("Sorry, I couldn't understand the type of account to create.");
        }
    }

    private void createSavingsAccount() {
        System.out.print("Enter account number for Savings Account: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter interest rate: ");
        double interestRate = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter minimum balance: ");
        double minimumBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        customer.createSavingsAccount(accountNumber, interestRate, minimumBalance);
        System.out.println("Savings account created successfully!");
    }

    private void createFixedAccount() {
        System.out.print("Enter account number for Fixed Account: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter term in years: ");
        int termInYears = scanner.nextInt();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter initial deposit: ");
        double minimumBalance = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        double interestRate = scanner.nextDouble();
        scanner.nextLine();
        customer.createFixedAccount(accountNumber, interestRate, minimumBalance ,termInYears);
        System.out.println("Fixed account created successfully!");
    }

    private void createCurrentAccount() {
        System.out.print("Enter account number for Current Account: ");
        String accountNumber = scanner.nextLine();
        System.out.print("Enter overdraft limit: ");
        double overdraftLimit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        System.out.print("Enter initial deposit: ");
        double initialDeposit = scanner.nextDouble();
        scanner.nextLine(); // Consume newline
        customer.createCurrentAccount(accountNumber, overdraftLimit, initialDeposit);
        System.out.println("Current account created successfully!");
    }
    // Create a loan
private void createLoan() {
    System.out.print("Enter Loan ID: ");
    String loanId = scanner.nextLine();
    System.out.print("Enter Loan Amount: ");
    double loanAmount = scanner.nextDouble();
    System.out.print("Enter Interest Rate: ");
    double interestRate = scanner.nextDouble();
    System.out.print("Enter Loan Term in Years: ");
    int loanTermInYears = scanner.nextInt();
    scanner.nextLine(); // Consume newline
    System.out.print("Enter Start Date (yyyy-mm-dd): ");
    String startDateStr = scanner.nextLine(); // Parse or store start date as needed

    customer.createLoan(loanId, loanAmount, interestRate, loanTermInYears, new Date());
    System.out.println("Loan created successfully!");
}

// View all loans
private void viewLoans() {
    customer.viewAllLoans();
}

// Repay a loan
private void repayLoan() {
    System.out.print("Enter Loan ID to repay: ");
    String loanIdToRepay = scanner.nextLine();
    System.out.print("Enter payment amount: ");
    double paymentAmount = scanner.nextDouble();
    customer.repayLoan(loanIdToRepay, paymentAmount);
    System.out.println("Repayment processed.");
}

// Transfer loan amount to an account
private void transferLoanToAccount() {
    System.out.print("Enter Loan ID to transfer: ");
    String loanIdToTransfer = scanner.nextLine();
    customer.transferLoanToAccount(loanIdToTransfer);
    System.out.println("Loan transferred to account.");
}

// Close a loan
private void closeLoan() {
    System.out.print("Enter Loan ID to close: ");
    String loanIdToClose = scanner.nextLine();
    customer.closeLoan(loanIdToClose);
    System.out.println("Loan closed.");
}

}
