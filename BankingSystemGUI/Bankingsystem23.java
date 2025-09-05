import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import javax.swing.JOptionPane;


// Main class
public class Bankingsystem23 {
    private final  static Map<String, User> users = new HashMap<>();
    public static final List<Account> accounts = new ArrayList<>();
    public static final List<Loan> loans = new ArrayList<>();
    private final static List<Customer> customers = new ArrayList<>();
    public static void registerCustomer(String username, String password) {
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Customer newCustomer = new Customer(username, password);
            users.put(username, newCustomer);
            JOptionPane.showMessageDialog(null, "Customer registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Register a new bank staff
    public static void registerBankStaff(String username, String password) {
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            BankStaff newStaff = new BankStaff(username, password);
            users.put(username, newStaff);
            JOptionPane.showMessageDialog(null, "Bank Staff registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Register a new manager
    public static void registerManager(String username, String password) {
        if (users.containsKey(username)) {
            JOptionPane.showMessageDialog(null, "Username already exists!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Manager newManager = new Manager(username, password);
            users.put(username, newManager);
            JOptionPane.showMessageDialog(null, "Manager registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    // Helper function to find users by username
    public static User findUserByUsername(String username) {
        return users.get(username);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        
        

        System.out.println("Welcome to the Banking System!");
        while (true) {
            System.out.println("-------------------------");
            System.out.println("1. Register Customer");
            System.out.println("2. Register Bank Staff");
            System.out.println("3. Register Manager");
            System.out.println("4. Login");
            System.out.println("5. Exit");
            System.out.println("-------------------------");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    registerCustomer(scanner);
                    break;

                case 2:
                    registerBankStaff(scanner);
                    break;

                case 3:
                    registerManager(scanner);
                    break;

                case 4:
                    login(scanner);
                    break;

                case 5:
                    System.out.println("Exiting the system...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void registerCustomer(Scanner scanner) {
        System.out.print("Enter username: ");
    String username = scanner.nextLine();
    
    // Check if username already exists
    if (users.containsKey(username)) {
        System.out.println("Username already exists. Please choose a different username.");
        return;
    }
    
    System.out.print("Enter password: ");
    String password = scanner.nextLine();
    Customer newCustomer = new Customer(username, password);
    customers.add(newCustomer);
    users.put(username, newCustomer);
    System.out.println("Customer registered successfully.");
    }

    private static void registerBankStaff(Scanner scanner) {
        System.out.print("Enter staff username: ");
    String username = scanner.nextLine();
    
    // Check if username already exists
    if (users.containsKey(username)) {
        System.out.println("Username already exists. Please choose a different username.");
        return;
    }
    
    System.out.print("Enter staff password: ");
    String password = scanner.nextLine();
    BankStaff newStaff = new BankStaff(username, password);
    users.put(username, newStaff);
    System.out.println("Bank Staff registered successfully.");
    }

    private static void registerManager(Scanner scanner) {
        System.out.print("Enter manager username: ");
        String username = scanner.nextLine();
        
        // Check if username already exists
        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }
        
        System.out.print("Enter manager password: ");
        String password = scanner.nextLine();
        Manager newManager = new Manager(username, password);
        users.put(username, newManager);
        System.out.println("Manager registered successfully.");
    }
    

    private static void login(Scanner scanner) {
        System.out.print("Enter username: ");
        String loginUsername = scanner.nextLine();
        System.out.print("Enter password: ");
        String loginPassword = scanner.nextLine();
        User user = users.get(loginUsername);
    
        if (user != null && user.login(loginPassword)) {
            if (user instanceof Customer) {
                customerMenu((Customer) user, scanner);
            } else if (user instanceof BankStaff) {
                // Initialize the staff object correctly
                BankStaff staff = (BankStaff) user;  // Cast user to BankStaff
    
                // Now, call the bankStaffMenu method with the initialized staff object
                bankStaffMenu(staff, customers, scanner);
    
            } else if (user instanceof Manager) {
                managerMenu((Manager) user, scanner);
            }
        } else {
            System.out.println("Invalid username or password.");
        }
    }
    

    private static void customerMenu(Customer customer, Scanner scanner) {
        
        
        CustomerAI customerAI = new CustomerAI(customer);  // Instantiate CustomerAI
    
        while (true) {
            System.out.println("-------------------------");
            System.out.println("\nCustomer Menu");
            System.out.println("1. Create Savings Account");
            System.out.println("2. Create Fixed Account");
            System.out.println("3. Create Current Account");
            System.out.println("4. View Balance");
            System.out.println("5. Deposit");
            System.out.println("6. Withdraw");
            System.out.println("7. Transaction History");        
            System.out.println("8. Close Account");
            System.out.println("9. View Account Details");
            System.out.println("10. Create Loan");            // New option for creating a loan
            System.out.println("11. View Loans");             // New option for viewing loans
            System.out.println("12. Repay Loan");             // New option for repaying a loan
            System.out.println("13. Transfer Loan to Account"); // New option for transferring loan to account
            System.out.println("14. Close Loan");             // New option for closing a loan
            System.out.println("15. Use AI Assistant");      // New option for AI
            System.out.println("16. Logout");
            System.out.println("-------------------------");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    System.out.print("Enter account number for Savings Account: ");
                    String savingsAccountNumber = scanner.nextLine();
    
                    System.out.print("Enter minimumBalance: ");
                    double minimumBalance = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left by nextDouble()
                    
                    System.out.print("Enter interestRate: ");
                    double interestRate = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline left by nextDouble() again
                    
    
                    customer.createSavingsAccount(savingsAccountNumber, interestRate, minimumBalance);
                    break;
    
                case 2:
                    System.out.print("Enter account number for Fixed Account: ");
                    String fixedAccountNumber = scanner.nextLine();
                    System.out.print("Enter interestRate: ");
                    double interest = scanner.nextDouble();
                    scanner.nextLine();
                    System.out.print("Enter minimumBalance: ");
                    double mb = scanner.nextDouble();
                    scanner.nextLine(); 
                    System.out.print("Enter term in years: ");
                    int termInYears = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    customer.createFixedAccount(fixedAccountNumber, interest, mb, termInYears);
                    break;
    
                case 3:
                    System.out.print("Enter account number for Current Account: ");
                    String currentAccountNumber = scanner.nextLine();
    
                    System.out.print("Enter overdraft limit: ");
                    double overdraftLimit = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
    
                    System.out.print("Enter initial deposit: ");
                    double currentInitialDeposit = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
    
                    customer.createCurrentAccount(currentAccountNumber, overdraftLimit, currentInitialDeposit);
                    break;
    
                case 4:
                    System.out.print("Enter account number to view balance: ");
                    String accNumView = scanner.nextLine();
                    customer.viewBalance(accNumView);
                    break;
    
                case 5:
                    System.out.print("Enter account number to deposit: ");
                    String accNumDeposit = scanner.nextLine();
    
                    System.out.print("Enter amount to deposit: ");
                    double depositAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
    
                    customer.deposit(accNumDeposit, depositAmount);
                    break;
    
                case 6:
                    System.out.print("Enter account number to withdraw: ");
                    String accNumWithdraw = scanner.nextLine();
    
                    System.out.print("Enter amount to withdraw: ");
                    double withdrawAmount = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline
    
                    customer.withdraw(accNumWithdraw, withdrawAmount);
                    break;
    
                case 7:
                    System.out.print("Enter account number for transaction history: ");
                    String accNumHistory = scanner.nextLine();
                    customer.viewTransactionHistory(accNumHistory);
                    break;
    
                case 8:
                    System.out.print("Enter account number to close: ");
                    String accNumClose = scanner.nextLine();
                    boolean isClosed = customer.closeAccount(accNumClose);
                    if (isClosed) {
                        System.out.println("Account " + accNumClose + " closed successfully.");
                    } else {
                        System.out.println("Account not found or could not be closed.");
                    }
                    break;
    
                case 9:
                    customer.viewAccountDetails();
                    break;
    
                    
                case 10:
                    // Create a new loan
                    System.out.print("Enter Loan ID: ");
                    String loanId = scanner.nextLine();
                    System.out.print("Enter Loan Amount: ");
                    double loanAmount = scanner.nextDouble();
                    
                    // Avoid redeclaring the variable interestRate
                    System.out.print("Enter Interest Rate: ");
                    interestRate = scanner.nextDouble();  // Use the already declared interestRate variable
                    
                    System.out.print("Enter Loan Term in Years: ");
                    int loanTermInYears = scanner.nextInt();
                    scanner.nextLine();  // Consume the newline
                    
                    System.out.print("Enter Start Date (yyyy-mm-dd): ");
                    String startDateStr = scanner.nextLine(); // You can parse this into a Date object later
                    
                    // Create loan object and add to customer loans
                    customer.createLoan(loanId, loanAmount, interestRate, loanTermInYears, new Date()); // Use new Date() or parse startDateStr to a Date object
                    break;
                case 11:
                    // View loans logic
                    customer.viewAllLoans();
                    break;
                case 12:
                    // Repay loan logic
                    System.out.print("Enter Loan ID to repay: ");
                    String loanIdToRepay = scanner.nextLine();
                    System.out.print("Enter payment amount: ");
                    double paymentAmount = scanner.nextDouble();
                    customer.repayLoan(loanIdToRepay, paymentAmount);
                    break;
                case 13:
                    // Transfer loan amount to account logic
                    System.out.print("Enter Loan ID to transfer: ");
                    String loanIdToTransfer = scanner.nextLine();
                    customer.transferLoanToAccount(loanIdToTransfer);
                    break;
                case 14:
                    // Close loan logic
                    System.out.print("Enter Loan ID to close: ");
                    String loanIdToClose = scanner.nextLine();
                    customer.closeLoan(loanIdToClose);
                    break;
                case 15:
                    // Trigger AI assistant
                    System.out.println("You are now interacting with the AI Assistant.");
                    while (true) {
                        System.out.print("Ask your query (use exit for): ");
                        String query = scanner.nextLine();
                        if (query.equalsIgnoreCase("exit")) {
                            System.out.println("Exiting AI assistant...");
                            break;
                        }
                        customerAI.handleQuery(query);  // Let the AI handle the query
                    }
                    break;
    
                case 16:
                    System.out.println("Logging out...");
                    return;
    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    

    
    

    public static void bankStaffMenu(BankStaff staff, List<Customer> customers, Scanner scanner) {
        boolean exit = false;
    
        while (!exit) {
            System.out.println("-------------------------");
            System.out.println("\n--- Bank Staff Menu ---");
            System.out.println("1. Register a new Customer");
            System.out.println("2. View Customer Accounts");
            System.out.println("3. Deposit to Customer Account");
            System.out.println("4. Withdraw from Customer Account");
            System.out.println("5. View Customer Transaction History");
            System.out.println("6. Close Customer Account");
            System.out.println("7. Exit");
            System.out.println("-------------------------");
            System.out.print("Choose an option: ");
    
            int choice = scanner.nextInt();
            scanner.nextLine();  // Consume newline
    
            switch (choice) {
                case 1:
                    // Register a new Customer
                    staff.registerCustomer(scanner, users, customers);
                    break;
    
                case 2:
                    // View all Customer Accounts
                    staff.viewCustomerAccounts(customers);
                    break;
    
                case 3:
                    // Deposit to Customer Account
                    System.out.print("Enter customer username: ");
                    String depositUsername = scanner.nextLine();
                    Customer depositCustomer = findCustomerByUsername(customers, depositUsername);
    
                    if (depositCustomer != null) {
                        System.out.print("Enter account number: ");
                        String depositAccountNumber = scanner.nextLine();
                        System.out.print("Enter amount to deposit: ");
                        double depositAmount = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline
                        staff.depositToCustomerAccount(depositUsername, depositAccountNumber, depositAmount, customers);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
    
                case 4:
                    // Withdraw from Customer Account
                    System.out.print("Enter customer username: ");
                    String withdrawUsername = scanner.nextLine();
                    Customer withdrawCustomer = findCustomerByUsername(customers, withdrawUsername);
    
                    if (withdrawCustomer != null) {
                        System.out.print("Enter account number: ");
                        String withdrawAccountNumber = scanner.nextLine();
                        System.out.print("Enter amount to withdraw: ");
                        double amount = scanner.nextDouble();
                        scanner.nextLine();  // Consume newline
                        staff.withdrawFromCustomerAccount(withdrawUsername, withdrawAccountNumber, amount, customers);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
    
                case 5:
                    // View Customer Transaction History
                    System.out.print("Enter customer username: ");
                    String transUsername = scanner.nextLine();
                    Customer transCustomer = findCustomerByUsername(customers, transUsername);
    
                    if (transCustomer != null) {
                        System.out.print("Enter account number: ");
                        String transAccountNumber = scanner.nextLine();
                        staff.viewCustomerTransactionHistory(transUsername, transAccountNumber, customers);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
    
                case 6:
                    // Close Customer Account
                    System.out.print("Enter customer username: ");
                    String closeUsername = scanner.nextLine();
                    Customer closeCustomer = findCustomerByUsername(customers, closeUsername);
    
                    if (closeCustomer != null) {
                        System.out.print("Enter account number to close: ");
                        String closeAccountNumber = scanner.nextLine();
                        staff.closeCustomerAccount(closeCustomer, closeAccountNumber);
                    } else {
                        System.out.println("Customer not found.");
                    }
                    break;
    
                case 7:
                    // Exit
                    exit = true;
                    break;
    
                default:
                    System.out.println("Invalid choice. Please select a valid option.");
                    break;
            }
        }
    }
    
    private static Customer findCustomerByUsername(List<Customer> customers, String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }
    
    private static void managerMenu(Manager manager, Scanner scanner) {
        while (true) {
            System.out.println("-------------------------");
            System.out.println("\nManager Menu");
            System.out.println("1. Generate Reports");
            System.out.println("2. Add Bank Staff");
            System.out.println("3. Remove Bank Staff");
            System.out.println("4. View All Bank Staff");
            System.out.println("5. View Customer Accounts");
            System.out.println("6. Logout");
            System.out.println("-------------------------");
    
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
    
            switch (choice) {
                case 1:
                    manager.generateReports();
                    break;
    
                case 2:
                    System.out.print("Enter bank staff username: ");
                    String staffUsername = scanner.nextLine();
                    
                    // Check if username already exists in users
                    if (users.containsKey(staffUsername)) {
                        System.out.println("Username already exists. Please choose a different username.");
                    } else {
                        System.out.print("Enter bank staff password: ");
                        String staffPassword = scanner.nextLine();
                        BankStaff newStaff = new BankStaff(staffUsername, staffPassword);
                        manager.addBankStaff(newStaff);
                        users.put(staffUsername, newStaff);
                        System.out.println("Bank staff added successfully.");
                    }
                    break;
    
                case 3:
                    System.out.print("Enter the username of the bank staff to remove: ");
                    String staffToRemove = scanner.nextLine();
                    manager.removeBankStaff(staffToRemove);
                    
                    // Remove from users map if staff exists
                    if (users.containsKey(staffToRemove)) {
                        users.remove(staffToRemove);
                        System.out.println("Bank staff removed successfully.");
                    } else {
                        System.out.println("Bank staff not found.");
                    }
                    break;
    
                case 4:
                    manager.viewBankStaff();
                    break;
    
                case 5:
                    manager.viewCustomerAccounts(customers);
                    break;
    
                case 6:
                    System.out.println("Logging out...");
                    return;
    
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
}
