package model;
import java.util.ArrayList;
import java.util.List;

public class User {
    private final long userId;          // this should never change
    private String fullName;
    private String mobileNumber;
    private String pin;
    private final List<String> transactionHistory = new ArrayList<>();    // transaction list should be permanent

    public User(long userId, String mobileNumber, String fullName, String pin) {
        this.userId = userId;
        this.mobileNumber = mobileNumber;
        this.fullName = fullName;
        this.pin = pin;
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

    // =====================================

    // add to transaction history (to be moved to a new services package later)
    public void addTransaction(String description) {
        transactionHistory.add(description);
    }

    public List<String> getTransactionHistory() {
        return new ArrayList<>(transactionHistory);   // a copy so outsiders can't edit it
    }

    public boolean checkpin(String attempt) {
        return pin.equals(attempt);
    }
}