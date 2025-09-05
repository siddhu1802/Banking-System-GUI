import java.awt.*;
import java.util.Date;
import java.util.List;
import javax.swing.*;

public class BankingSystemGUI {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame("Banking System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(6, 1, 10, 10));

        JLabel welcomeLabel = new JLabel("Welcome to the Banking System!", JLabel.CENTER);
        mainPanel.add(welcomeLabel);

        JButton registerCustomerButton = new JButton("Register Customer");
        JButton registerStaffButton = new JButton("Register Bank Staff"); 
        JButton registerManagerButton = new JButton("Register Manager");
        JButton loginButton = new JButton("Login");
        JButton exitButton = new JButton("Exit");

        mainPanel.add(registerCustomerButton);
        mainPanel.add(registerStaffButton);
        mainPanel.add(registerManagerButton);
        mainPanel.add(loginButton);
        mainPanel.add(exitButton);

        frame.add(mainPanel);

        registerCustomerButton.addActionListener(e -> showRegisterForm("Customer"));
        registerStaffButton.addActionListener(e -> showRegisterForm("Bank Staff"));
        registerManagerButton.addActionListener(e -> showRegisterForm("Manager"));
        loginButton.addActionListener(e -> showLoginForm());
        exitButton.addActionListener(e -> System.exit(0));

        frame.setVisible(true);
    }

    private static void showRegisterForm(String role) {
        JFrame registerFrame = new JFrame("Register " + role);
        registerFrame.setSize(400, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (!username.isEmpty() && !password.isEmpty()) {
                if (role.equals("Customer")) {
                    Bankingsystem23.registerCustomer(username, password);
                } else if (role.equals("Bank Staff")) {
                    Bankingsystem23.registerBankStaff(username, password);
                } else if (role.equals("Manager")) {
                    Bankingsystem23.registerManager(username, password);
                }
                JOptionPane.showMessageDialog(registerFrame, role + " registered successfully!");
                registerFrame.dispose();
            } else {
                JOptionPane.showMessageDialog(registerFrame, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        registerFrame.add(panel);
        registerFrame.setVisible(true);
    }

    private static void showLoginForm() {
        JFrame loginFrame = new JFrame("Login");
        loginFrame.setSize(400, 300);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            User user = Bankingsystem23.findUserByUsername(username);

            if (user != null && user.login(password)) {
                JOptionPane.showMessageDialog(loginFrame, "Login successful!");
                loginFrame.dispose();

                if (user instanceof Customer) {
                    showCustomerMenu((Customer) user);
                } else if (user instanceof BankStaff) {
                    showBankStaffMenu((BankStaff) user);
                } else if (user instanceof Manager) {
                    showManagerMenu((Manager) user);
                }
            } else {
                JOptionPane.showMessageDialog(loginFrame, "Invalid credentials.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        loginFrame.add(panel);
        loginFrame.setVisible(true);
    }

    private static void showCustomerMenu(Customer customer) {
        JFrame customerFrame = new JFrame("Customer Menu");
        customerFrame.setSize(400, 600);
        JPanel panel = new JPanel(new GridLayout(16, 1, 10, 10));
    
        JButton createSavingsAccountButton = new JButton("Create Savings Account");
        JButton createFixedAccountButton = new JButton("Create Fixed Account");
        JButton createCurrentAccountButton = new JButton("Create Current Account");
        JButton viewBalanceButton = new JButton("View Balance");
        JButton depositButton = new JButton("Deposit");
        JButton withdrawButton = new JButton("Withdraw");
        JButton transactionHistoryButton = new JButton("Transaction History");
        JButton closeAccountButton = new JButton("Close Account");
        JButton viewAccountDetailsButton = new JButton("View Account Details");
        JButton createLoanButton = new JButton("Create Loan");
        JButton viewLoansButton = new JButton("View Loans");
        JButton repayLoanButton = new JButton("Repay Loan");
        JButton transferLoanToAccountButton = new JButton("Transfer Loan to Account");
        JButton closeLoanButton = new JButton("Close Loan");
        JButton logoutButton = new JButton("Logout");
        
        panel.add(createSavingsAccountButton);
        panel.add(createFixedAccountButton);
        panel.add(createCurrentAccountButton);
        panel.add(viewBalanceButton);
        panel.add(depositButton);
        panel.add(withdrawButton);
        panel.add(transactionHistoryButton);
        panel.add(closeAccountButton);
        panel.add(viewAccountDetailsButton);
        panel.add(createLoanButton);
        panel.add(viewLoansButton);
        panel.add(repayLoanButton);
        panel.add(transferLoanToAccountButton);
        panel.add(closeLoanButton);
        panel.add(logoutButton);
    
        customerFrame.add(panel);
    
        createSavingsAccountButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Savings Account Number:");
                if (customer.hasAccount(accountNumber)) { 
                    JOptionPane.showMessageDialog(customerFrame, "Account already exists.");
                } else {
                    double minimumBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter Minimum Balance:"));
                    double interestRate = Double.parseDouble(JOptionPane.showInputDialog("Enter Interest Rate:"));
                    customer.createSavingsAccount(accountNumber, interestRate, minimumBalance);
                    JOptionPane.showMessageDialog(customerFrame, "Savings Account created successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to create Savings Account.");
            }
        });
    
        createFixedAccountButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Fixed Account Number:");
                if (customer.hasAccount(accountNumber)) {
                    JOptionPane.showMessageDialog(customerFrame, "Account already exists.");
                } else {
                    double interestRate = Double.parseDouble(JOptionPane.showInputDialog("Enter Interest Rate:"));
                    double minimumBalance = Double.parseDouble(JOptionPane.showInputDialog("Enter Minimum Balance:"));
                    int termInYears = Integer.parseInt(JOptionPane.showInputDialog("Enter Term in Years:"));
                    customer.createFixedAccount(accountNumber, interestRate, minimumBalance, termInYears);
                    JOptionPane.showMessageDialog(customerFrame, "Fixed Account created successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to create Fixed Account.");
            }
        });
        
        createCurrentAccountButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Current Account Number:");
                if (customer.hasAccount(accountNumber)) {
                    JOptionPane.showMessageDialog(customerFrame, "Account already exists.");
                } else {
                    double overdraftLimit = Double.parseDouble(JOptionPane.showInputDialog("Enter Overdraft Limit:"));
                    double initialDeposit = Double.parseDouble(JOptionPane.showInputDialog("Enter Initial Deposit:"));
                    customer.createCurrentAccount(accountNumber, overdraftLimit, initialDeposit);
                    JOptionPane.showMessageDialog(customerFrame, "Current Account created successfully.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to create Current Account.");
            }
        });
        
        viewBalanceButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number to View Balance:");
                Account account = customer.getAccount(accountNumber); 
                if (account != null) {
                    double balance = account.getBalance(); 
                    JOptionPane.showMessageDialog(customerFrame, "Account Number: " + accountNumber + "\nBalance: $" + balance);
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Account not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to retrieve balance.");
            }
        });
          
        depositButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number for Deposit:");
                Account account = customer.getAccount(accountNumber); 
                if (account != null) {
                    double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Deposit Amount:"));
                    if (amount > 0) {
                        account.deposit(amount); 
                        JOptionPane.showMessageDialog(customerFrame, "Deposit successful! New balance: $" + account.getBalance());
                    } else {
                        JOptionPane.showMessageDialog(customerFrame, "Please enter a positive amount.");
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Account not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to deposit amount.");
            }
        });
    
        withdrawButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number for Withdrawal:");
                Account account = customer.getAccount(accountNumber);
                if (account != null) {
                    double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter Withdrawal Amount:"));
                    if (amount > 0 && account.getBalance() >= amount) {
                        account.withdraw(amount); 
                        JOptionPane.showMessageDialog(customerFrame, "Withdrawal successful! New balance: $" + account.getBalance());
                    } else if (amount <= 0) {
                        JOptionPane.showMessageDialog(customerFrame, "Please enter a positive amount.");
                    } else {
                        JOptionPane.showMessageDialog(customerFrame, "Insufficient funds.");
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Account not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to withdraw amount.");
            }
        });        
    
        transactionHistoryButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number to View Transaction History:");
                Account account = customer.getAccount(accountNumber); 
                if (account != null) {
                    List<Transaction> transactions = account.getTransactions();
                    if (transactions.isEmpty()) {
                        JOptionPane.showMessageDialog(customerFrame, "No transactions found for this account.");
                    } else {
                        StringBuilder history = new StringBuilder("Transaction History:\n\n");
                        for (Transaction transaction : transactions) {
                            history.append(transaction.getType()).append(" of ")
                                   .append(transaction.getAmount()).append(" on ")
                                   .append(transaction.getDate().toString()).append("\n");
                        }
                        JOptionPane.showMessageDialog(customerFrame, history.toString());
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Account not found.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to retrieve transaction history.");
            }
        });

        closeAccountButton.addActionListener(e -> {
            try {
                String accountNumber = JOptionPane.showInputDialog("Enter Account Number to Close:");
                boolean success = customer.closeAccount(accountNumber);
                if (success) {
                    JOptionPane.showMessageDialog(customerFrame, "Account closed successfully.");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Failed to close account.");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to close account.");
            }
        });
    
        viewAccountDetailsButton.addActionListener(e -> {
            try {
                String accountDetails = customer.viewAccountDetails();
                JOptionPane.showMessageDialog(customerFrame, accountDetails, "Account Details", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to retrieve account details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        createLoanButton.addActionListener(e -> {
            try {
                String loanId = JOptionPane.showInputDialog("Enter Loan ID:");
                double loanAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter Loan Amount:"));
                double interestRate = Double.parseDouble(JOptionPane.showInputDialog("Enter Interest Rate:"));
                int termInYears = Integer.parseInt(JOptionPane.showInputDialog("Enter Loan Term in Years:"));
                String dateInput = JOptionPane.showInputDialog("Enter Start Date (yyyy-MM-dd):");
                customer.createLoan(loanId, loanAmount, interestRate, termInYears, new Date());
                JOptionPane.showMessageDialog(customerFrame, "Loan created successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to create loan.");
            }
        });
    
        viewLoansButton.addActionListener(e -> {
            try {

                if (customer instanceof Customer) {
                    String loanDetails = customer.viewAllLoans(); 
                    
                    if (loanDetails.isEmpty()) {
                        JOptionPane.showMessageDialog(customerFrame, "No loans available.", "Loan Details", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(customerFrame, loanDetails, "Loan Details", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "No valid customer found to retrieve loans.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "An error occurred while retrieving loan details.", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace(); 
            }
        });
        
        repayLoanButton.addActionListener(e -> {
            try {
                String loanId = JOptionPane.showInputDialog("Enter Loan ID:");
        
                double loanBalance = customer.getRemainingBalance(loanId); 
                if (loanBalance == -1) {
                    JOptionPane.showMessageDialog(customerFrame, "Loan ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                double repayAmount = Double.parseDouble(JOptionPane.showInputDialog("Enter Repayment Amount:"));
        
                if (repayAmount <= 0) {
                    JOptionPane.showMessageDialog(customerFrame, "Repayment amount must be greater than zero.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                if (repayAmount >= loanBalance) {
                    customer.repayLoan(loanId, loanBalance);
                    JOptionPane.showMessageDialog(customerFrame, "Loan fully repaid. Loan ID: " + loanId);
                } else {
                    customer.repayLoan(loanId, repayAmount);
                    JOptionPane.showMessageDialog(customerFrame, "Partial repayment successful. Remaining loan balance: $" + (loanBalance - repayAmount));
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(customerFrame, "Invalid input. Please enter a numeric value for the repayment amount.", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "An error occurred while processing the repayment.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        transferLoanToAccountButton.addActionListener(e -> {
            try {
                String loanId = JOptionPane.showInputDialog("Enter Loan ID to Transfer to Account:");
        
                double loanBalance = customer.getRemainingBalance(loanId);
        
                if (loanBalance == -1) {
                    JOptionPane.showMessageDialog(customerFrame, "Loan ID not found.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
        
                boolean transferSuccessful = customer.transferLoanToAccount(loanId);
        
                if (transferSuccessful) {
                    JOptionPane.showMessageDialog(customerFrame, "Loan amount of $" + loanBalance + " transferred to your account.");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Failed to transfer the loan amount.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(customerFrame, "Failed to transfer loan.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
closeLoanButton.addActionListener(e -> {
    try {
        String loanId = JOptionPane.showInputDialog(customerFrame, "Enter Loan ID to close:");
        
        if (loanId != null && !loanId.trim().isEmpty()) {
            Loan loan = customer.findLoanById(loanId);
            
            if (loan != null) {
                if (loan.getRemainingBalance() == 0) {
                    customer.closeLoan(loanId);
                    JOptionPane.showMessageDialog(customerFrame, "Loan cleared and closed successfully.");
                } else {
                    JOptionPane.showMessageDialog(customerFrame, "Please clear the loan balance before closing.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(customerFrame, "Loan not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(customerFrame, "Invalid Loan ID entered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    } catch (Exception ex) {
        JOptionPane.showMessageDialog(customerFrame, "An error occurred while closing the loan.", "Error", JOptionPane.ERROR_MESSAGE);
        ex.printStackTrace(); 
    }
});
        
        logoutButton.addActionListener(e -> {
            customerFrame.dispose();
        });
    
        customerFrame.setVisible(true);
    }
    
    private static void showBankStaffMenu(BankStaff staff) {
        JFrame staffFrame = new JFrame("Bank Staff Menu");
        staffFrame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(7, 1, 10, 10));
    
        JButton registerCustomerButton = new JButton("Register New Customer");
        JButton viewCustomerAccountsButton = new JButton("View Customer Accounts");
        JButton depositToCustomerAccountButton = new JButton("Deposit to Customer Account");
        JButton withdrawFromCustomerAccountButton = new JButton("Withdraw from Customer Account");
        JButton viewCustomerTransactionHistoryButton = new JButton("View Customer Transaction History");
        JButton closeCustomerAccountButton = new JButton("Close Customer Account");
        JButton exitButton = new JButton("Exit");
    
        panel.add(registerCustomerButton);
        panel.add(viewCustomerAccountsButton);
        panel.add(depositToCustomerAccountButton);
        panel.add(withdrawFromCustomerAccountButton);
        panel.add(viewCustomerTransactionHistoryButton);
        panel.add(closeCustomerAccountButton);
        panel.add(exitButton);
    
        staffFrame.add(panel);
    
        registerCustomerButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter customer username:");
            String password = JOptionPane.showInputDialog("Enter customer password:");
            
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(staffFrame, "Username and password cannot be empty.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Bankingsystem23.registerCustomer(username, password);
                JOptionPane.showMessageDialog(staffFrame, "Customer account created successfully.", 
                                              "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        viewCustomerAccountsButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username to view accounts:");
        
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
        
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
        
                StringBuilder accountsInfo = new StringBuilder("Customer Accounts:\n");
        
                for (Account account : customerObj.getAccounts()) {
                    accountsInfo.append("Account Number: ").append(account.getAccountNumber())
                                 .append(", Balance: ").append(account.getBalance()).append("\n");
                }
        
                JOptionPane.showMessageDialog(staffFrame, accountsInfo.toString(), 
                                              "Customer Accounts", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(staffFrame, "Customer not found.", 
                                              "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
              
        depositToCustomerAccountButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username:");
            String accountNumber = JOptionPane.showInputDialog("Enter account number:");
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter deposit amount:"));
            
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
            
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
                Account account = customerObj.getAccount(accountNumber);
                
                if (account != null) {
                    account.deposit(amount);
                    JOptionPane.showMessageDialog(staffFrame, "Deposit successful.");
                } else {
                    JOptionPane.showMessageDialog(staffFrame, "Account not found.");
                }
            } else {
                JOptionPane.showMessageDialog(staffFrame, "Customer not found.");
            }
        });
        
        withdrawFromCustomerAccountButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username:");
            String accountNumber = JOptionPane.showInputDialog("Enter account number:");
            double amount = Double.parseDouble(JOptionPane.showInputDialog("Enter withdraw amount:"));
            
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
            
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
                Account account = customerObj.getAccount(accountNumber);
                
                if (account != null) {
                    account.withdraw(amount);
                    
                    JOptionPane.showMessageDialog(staffFrame, "Withdrawal of " + amount + " successful.");
                } else {
                    JOptionPane.showMessageDialog(staffFrame, "Account not found.");
                }
            } else {
                JOptionPane.showMessageDialog(staffFrame, "Customer not found.");
            }
        });
        
        viewCustomerTransactionHistoryButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username:");
            String accountNumber = JOptionPane.showInputDialog("Enter account number:");
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
                Account account = customerObj.getAccount(accountNumber);
                if (account != null) {
                    StringBuilder history = new StringBuilder("Transaction History:\n");
                    for (Transaction transaction : account.getTransactions()) {
                        history.append(transaction.toString()).append("\n");
                    }
                    JOptionPane.showMessageDialog(staffFrame, history.toString());
                } else {
                    JOptionPane.showMessageDialog(staffFrame, "Account not found.");
                }
            } else {
                JOptionPane.showMessageDialog(staffFrame, "Customer not found.");
            }

        });
        closeCustomerAccountButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username:");
            String accountNumber = JOptionPane.showInputDialog("Enter account number:");
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
                boolean success = customerObj.closeAccount(accountNumber);
                if (success) {
                    JOptionPane.showMessageDialog(staffFrame, "Account closed successfully.");
                } else {
                    JOptionPane.showMessageDialog(staffFrame, "Failed to close account.");
                }
            } else {
                JOptionPane.showMessageDialog(staffFrame, "Customer not found.");
            }
        });
        exitButton.addActionListener(e -> {
            staffFrame.dispose();
        });
    
        staffFrame.setVisible(true);
    }
    
    private static void showManagerMenu(Manager manager) {
        JFrame managerFrame = new JFrame("Manager Menu");
        managerFrame.setSize(400, 400);
        JPanel panel = new JPanel(new GridLayout(6, 1, 10, 10));
    
        JButton generateReportsButton = new JButton("Generate Reports");
        JButton addBankStaffButton = new JButton("Add Bank Staff");
        JButton removeBankStaffButton = new JButton("Remove Bank Staff");
        JButton viewAllBankStaffButton = new JButton("View All Bank Staff");
        JButton viewCustomerAccountsButton = new JButton("View Customer Accounts");
        JButton logoutButton = new JButton("Log Out");
    
        panel.add(generateReportsButton);
        panel.add(addBankStaffButton);
        panel.add(removeBankStaffButton);
        panel.add(viewAllBankStaffButton);
        panel.add(viewCustomerAccountsButton);
        panel.add(logoutButton);
    
        managerFrame.add(panel);
    
        generateReportsButton.addActionListener(e -> {
            manager.generateReports();
            JOptionPane.showMessageDialog(managerFrame, "till in process not developerd yet!!", "Confirmation", JOptionPane.INFORMATION_MESSAGE);
        });
        addBankStaffButton.addActionListener(e -> {
            String username = JOptionPane.showInputDialog("Enter bank staff username:");
            String password = JOptionPane.showInputDialog("Enter bank staff password:");
            
            if (username == null || username.trim().isEmpty() || password == null || password.trim().isEmpty()) {
                JOptionPane.showMessageDialog(managerFrame, "Username and password cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                Bankingsystem23.registerBankStaff(username, password);
                JOptionPane.showMessageDialog(managerFrame, "Bank staff account created successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        
        removeBankStaffButton.addActionListener(e -> {
            String usernameToRemove = JOptionPane.showInputDialog("Enter the username of the Bank Staff to remove:");
        
            if (usernameToRemove == null || usernameToRemove.trim().isEmpty()) {
                JOptionPane.showMessageDialog(managerFrame, "Invalid username. Removal failed.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                User bankStaff = Bankingsystem23.findUserByUsername(usernameToRemove); 
        
                if (bankStaff != null && bankStaff instanceof BankStaff) {
                    manager.removeBankStaff(usernameToRemove);
                    JOptionPane.showMessageDialog(managerFrame, "Bank Staff removed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(managerFrame, "Bank Staff with username '" + usernameToRemove + "' does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        viewAllBankStaffButton.addActionListener(e -> {
            try {
                List<BankStaff> bankStaffList = BankStaff.getBankStaffList(); 
                
                if (bankStaffList != null && !bankStaffList.isEmpty()) {
                    StringBuilder staffDetails = new StringBuilder("List of Bank Staff:\n");
                    
                    for (BankStaff staff : bankStaffList) {
                        staffDetails.append(staff.getUsername()).append("\n");
                    }
                    
                    JOptionPane.showMessageDialog(managerFrame, staffDetails.toString(),
                                                  "Bank Staff List", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(managerFrame, "No bank staff found.", 
                                                  "Bank Staff List", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(managerFrame, "An error occurred while retrieving bank staff details.",
                                              "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });

        viewCustomerAccountsButton.addActionListener(e -> {
            String customerUsername = JOptionPane.showInputDialog("Enter customer username to view accounts:");
            User customer = Bankingsystem23.findUserByUsername(customerUsername);
            if (customer != null && customer instanceof Customer) {
                Customer customerObj = (Customer) customer;
                StringBuilder accountsInfo = new StringBuilder("Customer Accounts:\n");
                for (Account account : customerObj.getAccounts()) {
                    accountsInfo.append("Account Number: ").append(account.getAccountNumber())
                            .append(", Balance: ").append(account.getBalance()).append("\n");
                }
                JOptionPane.showMessageDialog(managerFrame, accountsInfo.toString());
            } else {
                JOptionPane.showMessageDialog(managerFrame, "Customer not found.");
            }
        }); 

        logoutButton.addActionListener(e -> {
            managerFrame.dispose();
        });
    
        managerFrame.setVisible(true);
    }
    
}