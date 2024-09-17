public class BankingApp {
    public static void main(String[] args) {
        SavingsAccount savings = new SavingsAccount(1000, 0.5);
        savings.deposit(200);
        savings.applyInterest();
        savings.withdraw(500);
        System.out.println("Savings Account Balance: " + savings.getBalance());

        System.out.println("------------------------------------------------");

        CheckingAccount checking = new CheckingAccount(500, 500);
        checking.deposit(300);
        checking.withdraw(900);
        checking.withdraw(500);
        System.out.println("Checking Account Balance: " + checking.getBalance());
    }
}

class Account {
    protected double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public void deposit(double amount) {
        balance += amount;
        System.out.println("Deposited: " + amount + ". New Balance: " + balance);
    }

    public void withdraw(double amount) {
        if (amount <= balance) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Insufficient Funds!");
        }
    }

    public double getBalance() {
        return balance;
    }
}

class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(double balance, double interestRate) {
        super(balance);
        this.interestRate = interestRate;
    }

    public void applyInterest() {
        double interest = balance * interestRate;
        balance += interest;
        System.out.println("Savings Interest: " + interest + ". New Balance: " + balance);
    }
}

class CheckingAccount extends Account {
    private double overdraftLimit;

    public CheckingAccount(double balance, double overdraftLimit) {
        super(balance);
        this.overdraftLimit = overdraftLimit;
    }

    public void withdraw(double amount) {
        if (amount <= balance + overdraftLimit) {
            balance -= amount;
            System.out.println("Withdrawn: " + amount + ". New Balance: " + balance);
        } else {
            System.out.println("Insufficient Funds!");
        }
    }
}
