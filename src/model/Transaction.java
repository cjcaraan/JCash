package model;

public class Transaction {
    private long transactionId;
    private String type;
    private double amount;
    private String details;
    private String timeStamp;

    public Transaction(long transactionId, String type, double amount, String details, String timeStamp) {
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
        this.details = details;
        this.timeStamp = timeStamp;
    }

    // ===============getters===============

    public long getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }

    public String getDetails() {
        return details;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    // =====================================



}