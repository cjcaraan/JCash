package model;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final long userId;          // this should never change
    private String fullName;
    private String mobileNumber;
    private String pin;
    private double balance = 0;
    private final List<String> transactionHistory = new ArrayList<>();    // transaction list should be permanent

    public User(long userId, String mobileNumber, String fullName, String pin, double balance) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.pin = pin;
        this.balance = balance;
    }

    // ===============getters===============

    public long getUserId() {
        return userId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    // =====================================


    // ===============setters===============

    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    // =====================================

    
    // add to transaction history (to be moved to a new services package later)
    public void addTransaction(String description) {
        transactionHistory.add(description);
    }

    public void getTransactionHistory() {
        // return new ArrayList<>(transactionHistory);   // a copy so outsiders can't edit it
        // to be modified
    }

    public boolean checkpin(String attempt) {
        return pin.equals(attempt);
    }
}