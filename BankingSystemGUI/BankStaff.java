import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
class BankStaff extends User {
    private static List<BankStaff> bankStaffList = new ArrayList<>();

    public BankStaff(String username, String password) {
        super(username, password);
        bankStaffList.add(this);  // Automatically add to bankStaffList when created
    }

    public static List<BankStaff> getBankStaffList() {
        return bankStaffList;
    }

    public void viewCustomerAccounts(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.getUsername());
            for (Account account : customer.getAccounts()) {
                System.out.println("Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
            }
        }
    }

    public void selfRegister() {
        if (!bankStaffList.contains(this)) {
            bankStaffList.add(this);
            System.out.println("Bank staff " + this.getUsername() + " registered successfully.");
        } else {
            System.out.println("Already registered.");
        }
    }

    public String getUsername() {
        return this.username;
    }

    public void registerCustomer(Scanner scanner, Map<String, User> users, List<Customer> customers) {
        System.out.print("Enter new customer's username: ");
        String username = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return;
        }

        System.out.print("Enter new customer's password: ");
        String password = scanner.nextLine();
        Customer newCustomer = new Customer(username, password);
        customers.add(newCustomer);
        users.put(username, newCustomer);
        System.out.println("Customer registered successfully.");
    }

    public void depositToCustomerAccount(String customerUsername, String accountNumber, double amount, List<Customer> customers) {
        Customer customer = findCustomerByUsername(customerUsername, customers);
        if (customer != null) {
            customer.deposit(accountNumber, amount);
        } else {
            System.out.println("Customer not found.");
        }
    }

    public void withdrawFromCustomerAccount(String customerUsername, String accountNumber, double amount, List<Customer> customers) {
        Customer customer = findCustomerByUsername(customerUsername, customers);
        if (customer != null) {
            customer.withdraw(accountNumber, amount);
        } else {
            System.out.println("Customer not found.");
        }
    }

    public void viewCustomerTransactionHistory(String customerUsername, String accountNumber, List<Customer> customers) {
        Customer customer = findCustomerByUsername(customerUsername, customers);
        if (customer != null) {
            customer.viewTransactionHistory(accountNumber);
        } else {
            System.out.println("Customer not found.");
        }
    }

    private Customer findCustomerByUsername(String username, List<Customer> customers) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username)) {
                return customer;
            }
        }
        return null;
    }

    public void closeCustomerAccount(Customer customer, String accountNumber) {
        if (customer.closeAccount(accountNumber)) {
            System.out.println("Account " + accountNumber + " for customer " + customer.getUsername() + " has been successfully closed.");
        } else {
            System.out.println("Failed to close account " + accountNumber + ". Account may not exist.");
        }
    }
}
