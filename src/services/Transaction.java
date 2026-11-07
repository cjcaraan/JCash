package services;

import java.util.Scanner;


import model.User;

public class Transaction {

    public void cashIn (User user, Scanner scanner) {
        String input = scanner.nextLine().trim();
        double amount = Double.parseDouble(input);

        user.deposit(amount);
    }
    
}
