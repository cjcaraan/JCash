package services;

import java.util.Scanner;

import model.User;
import dao.UserDao;

public class Transaction {
    UserDao userDao = new UserDao();

    public void cashIn (User user, Scanner scanner) {
        System.out.print("Please enter amount to Cash-In: ");
        String input = scanner.nextLine().trim();
        double amount = Double.parseDouble(input);

        if (amount <= 0) {
            throw new IllegalArgumentException("Please enter valid amount.");
        }

        user.deposit(amount);
        userDao.adjustBalance(user.getMobileNumber(), amount);
    }

    public void transferToAnotherUser(User user, Scanner scanner) {
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

        System.out.println("Transfer Succesful!");
    }
    
}
