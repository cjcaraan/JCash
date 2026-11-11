package model;

import services.TransactionServices;

public class User {
    private final long userId;          // this should never change
    private String fullName;
    private String mobileNumber;
    private String pin;
    private double balance = 0;

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


    public void getTransactionHistory() {
        // return new ArrayList<>(transactionHistory);   // a copy so outsiders can't edit it
        // to be modified
    }

    public boolean checkpin(String attempt) {
        return pin.equals(attempt);
    }
}