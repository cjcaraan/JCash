package services;

import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

import model.*;
import dao.*;

public class TransactionServices {
    UserDao userDao = new UserDao();
    TransactionDao transactionDao = new TransactionDao();

    public void cashIn (User user, Scanner scanner) {
        String transactionType = "CASH_IN";

        System.out.print("Please enter amount to Cash-In: ");
        String input = scanner.nextLine().trim();
        double amount = Double.parseDouble(input);

        if (amount <= 0) {
            throw new IllegalArgumentException("Please enter valid amount.");
        }

        user.deposit(amount);
        userDao.adjustBalance(user.getMobileNumber(), amount);

        // log the transaction to database
        transactionDao.insert(transactionType, 
                                amount, 
                                detailsConstructor(transactionType, 
                                                "KIOSK", 
                                                user.getMobileNumber(), 
                                                amount));
    }

    public void transferToAnotherUser(User user, Scanner scanner) {
        String transactionType = "TRANSFER";

        System.out.print("Enter mobile number to send to: ");
        String otherAccountNumber = scanner.nextLine().trim();

        if (!userDao.isValidMobileNumber(otherAccountNumber)) {
            throw new IllegalArgumentException("This mobile number is not registered or doesn't exist.");
        }

        System.out.print("Please enter amount to transfer: ");
        String input = scanner.nextLine().trim();
        double amount = Double.parseDouble(input);

        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer failed! Please enter valid amount.");
        }

        if (userDao.getBalance(user.getMobileNumber()) < amount) {
            throw new IllegalArgumentException("Transfer failed! Insufficient balance.");
        }

        User otherAccount = userDao.findByMobile(otherAccountNumber);

        user.withdraw(amount);
        userDao.adjustBalance(user.getMobileNumber(), amount*-1);

        otherAccount.deposit(amount);
        userDao.adjustBalance(otherAccount.getMobileNumber(), amount);

        transactionDao.insert(transactionType, 
                        amount, 
                        detailsConstructor(transactionType, 
                                            user.getMobileNumber(), 
                                            otherAccount.getMobileNumber(), 
                                            amount));     // reflect on balance as deduction

        System.out.println("Transfer Succesful!");
    }

    private String detailsConstructor(String type, String from, String to, double amount) {
        String details;
        switch (type) {
            case "CASH_IN":
                details = type + String.format(" %.2f", amount) + " Php from " + from + " to " + to;
                return details;
            
            case "TRANSFER":
                details = type + String.format(" %.2f", amount) + " Php from " + from + " to " + to;
                return details;

            case "WITHDRAW":
                details = type + String.format(" %.2f", amount) + " Php from " + from + " to " + to;
                return details;
        }
        return "Could not log properly";
    }

    public void viewTransactionHistory(User user) {
        List<Transaction> transactionHistory = new ArrayList<>();

        transactionHistory = transactionDao.getTransactionHistory(user.getMobileNumber());

        System.out.println("Transaction History:");
        System.out.println("______________________________________________________________________________________________________________________________________________");
        System.out.println("|     Reference Number     |     Type     |     Amount (Php)     |                    Description                    |          Time          |");
        for (Transaction transaction : transactionHistory) {
            String transactionId = String.format("%d", transaction.getTransactionId());
            String type = transaction.getType();
            String amount = String.format("%.2f", transaction.getAmount());
            String details = transaction.getDetails();
            String[] detailsWords = details.trim().split("\\s+");
            String timeStamp = transaction.getTimeStamp();
            // check if user is the from or the to (amount is negative if from, positive if to)
            if (user.getMobileNumber().equals(detailsWords[detailsWords.length - 1])) { amount = "+" + amount; }
            else { amount = "-" + amount; }
            System.out.println(spaceGenerator(9) 
                                + transactionId 
                                + spaceGenerator(9) 
                                + spaceGenerator(3)
                                + type 
                                + spaceGenerator(3)
                                + spaceGenerator(9)
                                + amount 
                                + spaceGenerator(9)
                                + spaceGenerator(2)
                                + details 
                                + spaceGenerator(2)
                                + spaceGenerator(3)
                                + timeStamp
                                + spaceGenerator(3));
        }
    }

    private String spaceGenerator(int num) {
        String temp = "";
        for (int i = 0; i < num; i++) {
            temp += " ";
        }
        return temp;
    }

}
