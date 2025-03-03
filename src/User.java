import java.util.ArrayList;
import java.util.List;

public class User {
    private String cardNumber;
    private String pin;
    private String name;
    private double balance;
    private List<String> transactionHistory = new ArrayList<>();

    public User(String cardnumber, String pin, String name){
        this.cardNumber = cardnumber;
        this.pin = pin;
        this.balance = 0;
        this.name = name;
    }

    public boolean verifyPin(String pin) {
        if(pin.equals(this.pin)){
            return true;
        }
        return false;
    }

    public void deposit(double amount){
        balance += amount;
        transactionHistory.add("Deposited ₹" + amount);
    }

    public boolean withdraw(double amount){
        if(amount > 0 && balance >= amount){
            balance = balance - amount;
            transactionHistory.add("Withdrawal ₹" + amount);
            return true;
        }
        return false;
    }

    public double getBalance(){
        return balance;
    }

    public String getname(){
        return name;
    }

    public boolean transfer(String hiddenAccountNo, double amount, String name) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            transactionHistory.add("Transferred ₹" + amount + " to " + name+" A/c "+hiddenAccountNo );
            return true;
        }
        return false;
    }

    public void showTransactionHistory() {
        System.out.println("Transaction History:");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions yet.");
        } else {
            transactionHistory.forEach(System.out::println);
        }
    }


}
