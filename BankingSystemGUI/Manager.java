import java.util.List;

class Manager extends User {

    public Manager(String username, String password) {
        super(username, password);
    }

    public void addBankStaff(BankStaff staff) {
        if (!BankStaff.getBankStaffList().contains(staff)) {
            BankStaff.getBankStaffList().add(staff);
            System.out.println("Bank staff " + staff.getUsername() + " added successfully by manager.");
        } else {
            System.out.println("Bank staff already registered.");
        }
    }

    public void removeBankStaff(String username) {
        BankStaff staffToRemove = null;
        for (BankStaff staff : BankStaff.getBankStaffList()) {
            if (staff.getUsername().equals(username)) {
                staffToRemove = staff;
                break;
            }
        }

        if (staffToRemove != null) {
            BankStaff.getBankStaffList().remove(staffToRemove);
            System.out.println("Bank staff " + username + " removed successfully.");
        } else {
            //System.out.println("Bank staff with username " + username + " not found.");
        }
    }

    public void viewBankStaff() {
        if (BankStaff.getBankStaffList().isEmpty()) {
            System.out.println("No bank staff members.");
        } else {
            System.out.println("Bank Staff List:");
            for (BankStaff staff : BankStaff.getBankStaffList()) {
                System.out.println("- " + staff.getUsername());
            }
        }
    }
    public void viewCustomerAccounts(List<Customer> customers) {
        for (Customer customer : customers) {
            System.out.println("Customer: " + customer.username);
            for (Account account : customer.getAccounts()) {
                System.out.println("Account Number: " + account.getAccountNumber() + ", Balance: " + account.getBalance());
            }
        }
    }
    void generateReports()
    {
        System.out.println("till in process not developerd yet!!");
    }
   
}
