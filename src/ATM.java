import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ATM {
    private static Map<String, User> userDatabase = new HashMap<>();
    private User currentUser;
    Scanner scan = new Scanner(System.in);

    public void register() {
        System.out.println("---User Registration---");

        String cardNumber,name;
        System.out.print("Enter Your name: ");
        name = scan.nextLine();

        while (true) {
            System.out.print("Enter your 10-digit ATM Card Number: ");
            cardNumber = scan.nextLine();
            if (!cardNumber.matches("\\d{10}")) {
                System.out.println("Invalid card number! It must be exactly 10 digits.");
            } else if (userDatabase.containsKey(cardNumber)) {
                System.out.println("User already registered with this card number.");
            } else {
                break;
            }
        }

        System.out.print("Set your 4-digit PIN: ");
        String pin = scan.nextLine();

        if (!pin.matches("\\d{4}")) {
            System.out.println("Invalid PIN. Must be 4 digits.");
            return;
        }

        User newUser = new User(cardNumber, pin,name);
        userDatabase.put(cardNumber, newUser);

        System.out.println("Registration successful!\n");
        login();
    }

    public void login(){
        System.out.println("---User Login---");
        System.out.println("Enter your ATM card Number: ");
        String cardNumber = scan.nextLine();

        System.out.print("Enter your 4-digit PIN: ");
        String pin = scan.nextLine();

        if(userDatabase.containsKey(cardNumber) && userDatabase.get(cardNumber).verifyPin(pin)){
            currentUser =  userDatabase.get(cardNumber);
            System.out.println("Login successful! Welcome,"+currentUser.getname());
            menu();
        } else {
            System.out.println("Invalid Card Number or PIN. Try again.");
            login();
        }

    }


    public void menu() {
        while(true){

            System.out.println();

            System.out.println("\nATM Menu:");
            System.out.println("1. Deposit Money");
            System.out.println("2. Withdraw Money");
            System.out.println("3. Transfer Money");
            System.out.println("4. Check Balance");
            System.out.println("5. Transaction History");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scan.nextInt();

            switch (choice){
                case 1:
                    deposit();
                    break;

                case 2:
                    withdraw();
                    break;

                case 3:
                    transfer();
                    break;

                case 4:
                    checkBalance();
                    break;

                case 5:
                    transactionHistory();
                    break;

                case 6:
                    System.out.println("Exiting!!! Thank you for using ATM!");
                    return;

                default:
                    System.out.println("Invalid Choice !!!");
            }
        }
    }

    public void deposit() {
        System.out.print("Enter the amount to deposit: ");
        double amount = scan.nextDouble();
        if (amount > 0) {
            currentUser.deposit(amount);
            System.out.println("Deposited: ₹" + amount);
            System.out.println("Updated Balance: ₹" + currentUser.getBalance());
        } else {
            System.out.println("Amount should be greater than zero !!");
        }
    }

    public void withdraw() {
        System.out.print("Enter the amount to withdraw: ");
        double amount = scan.nextDouble();

        if (currentUser.withdraw(amount)) {
            System.out.println("Successfully withdraw ₹" + amount);
            System.out.println( "Updated balance: ₹" + currentUser.getBalance());
        } else {
            System.out.println("Insufficient balance or invalid amount!");
        }
    }

    public void transfer() {


        String cardNum;

        while (true) {
            System.out.print("Please enter recipient's 10-digit ATM Card Number: ");
            cardNum = scan.next();
            if (!cardNum.matches("\\d{10}")) {
                System.out.println("Invalid card number! It must be exactly 10 digits.");
            } else {
                break;
            }
        }
        scan.nextLine();
        String hiddenAccountNo = accountNumberPrivacy(cardNum);

        System.out.println("Please enter recipient's Name");
        String name=scan.nextLine();

        System.out.print("Enter the amount to transfer: ");
        double amount = scan.nextDouble();
        scan.nextLine(); // Consume newline

        if (currentUser.transfer(hiddenAccountNo, amount, name)) {
            System.out.println("Transfer Successful!");
            System.out.println("Updated balance: ₹" + currentUser.getBalance());
        } else {
            System.out.println("Insufficient balance for the transfer!");
        }

        System.out.println("Available balance is "+ currentUser.getBalance()); }

    //Hiding of Recipient's Account No.
    public String accountNumberPrivacy(String accountNo){

        int accountNumberLength=accountNo.length()-3;
        String hiddenDigits="";
        for(int i=0;i<accountNumberLength;i++) {
            hiddenDigits+="*";
        }
        String last3Digits=accountNo.substring(accountNo.length() - 3);
        return (hiddenDigits+last3Digits);
    }


    public void checkBalance() {
        System.out.println("Your current balance is: ₹" + currentUser.getBalance());
    }

    public void transactionHistory() {
        currentUser.showTransactionHistory();
    }
}
