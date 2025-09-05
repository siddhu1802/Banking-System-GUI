import java.util.Date;
import java.util.List;

public class Loan {
    private String loanId;
    private double loanAmount;
    private double interestRate;
    private int loanTermInYears;
    private Date startDate;
    private double remainingBalance;
    private double totalAmountToPay;
    private boolean isClosed;
    private static List<Loan> loans;

    // Constructor and other methods...

    public Loan(String loanId, double loanAmount, double interestRate, int loanTermInYears, Date startDate) {
        this.loanId = loanId;
        this.loanAmount = loanAmount;
        this.interestRate = interestRate;
        this.loanTermInYears = loanTermInYears;
        this.startDate = startDate;
        this.totalAmountToPay = calculateTotalAmountToPay();
        this.remainingBalance = totalAmountToPay;
    }
    public void closeLoan(String loanId) {
        Loan loan = findLoanById(loanId);
        if (loan != null && loan.getRemainingBalance() == 0) {
            loans.remove(loan);
            System.out.println("Loan " + loanId + " is successfully closed.");
        } else {
            System.out.println("Loan either doesn't exist or still has an outstanding balance.");
        }
    }
    

    // Calculate total amount to pay (loan amount + interest over the term)
    private double calculateTotalAmountToPay() {
        double totalInterest = (loanAmount * interestRate * loanTermInYears) / 100;
        return loanAmount + totalInterest;
    }
    // Method to create a loan
public void createLoan(String loanId, double loanAmount, double interestRate, int loanTermInYears, Date startDate) {
    Loan newLoan = new Loan(loanId, loanAmount, interestRate, loanTermInYears, startDate);
    loans.add(newLoan);
    System.out.println("Loan created with ID: " + loanId);
}


    

    // Method to view loan details
    // In Loan class
    public String viewLoanDetails() {
        StringBuilder loanDetails = new StringBuilder();
        loanDetails.append("Loan ID: ").append(loanId).append("\n")
                   .append("Loan Amount: ").append(loanAmount).append("\n")
                   .append("Interest Rate: ").append(interestRate).append("%\n")
                   .append("Loan Term (years): ").append(loanTermInYears).append("\n")
                   .append("Total Amount to Pay: ").append(totalAmountToPay).append("\n")
                   .append("Remaining Balance: ").append(remainingBalance).append("\n");
        return loanDetails.toString();
    }

    // Getters for loan fields
    public String getLoanId() {
        return loanId;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public double getLoanAmount() {
        return loanAmount;
    }

    public double getInterestRate() {
        return interestRate;
    }
    public void repay(double amount) {
        if (amount <= loanAmount) {
            loanAmount -= amount;  // Reduce the loan amount by the repayment
        } else {
            System.out.println("Repayment amount exceeds the loan balance.");
        }
    }
    
    public void makePayment(double amount) {
        if (amount > 0 && amount <= remainingBalance) {
            remainingBalance -= amount;
            System.out.println("Payment of " + amount + " made successfully. Remaining balance: " + remainingBalance);
        } else {
            System.out.println("Invalid payment amount. Please check and try again.");
        }
    }
    public void closeLoan() {
        if (remainingBalance == 0) {
            System.out.println("Loan " + loanId + " has been successfully closed.");
        } else {
            System.out.println("Loan has a remaining balance and cannot be closed.");
        }
    }
    public static Loan findLoanById(String loanId) {
        for (Loan loan : loans) {
            if (loan.getLoanId().equals(loanId)) {
                return loan;
            }
        }
        return null; // Return null if no loan with the given loanId is found
    }

    boolean isClosed() {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}