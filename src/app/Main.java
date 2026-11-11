package app;

import auth.*;
import model.*;
import services.*;
import util.*;

import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private Register register = new Register();
    private Login login = new Login();
    private User currentUser = null; // initially no one is logged in
    private TransactionServices transactionServices = new TransactionServices();

    public static void main(String[] args) {
        DatabaseUtil.initialize();
        new Main().run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showGuestMenu();
            } else {
                showMemberMenu();
            }
        }
        scanner.close();
        System.out.println("Goodbye!");
    }

    private boolean showGuestMenu() {
        System.out.println("=== Menu ===");
        System.out.println("1. Register a new account");
        System.out.println("2. Log in");
        System.out.println("3. Quit");
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                register.handleRegister(scanner);
                break;
            case "2":
                currentUser = login.handleLogin(scanner);
                break;
            case "3":
                return false;
            /*
             * case "4":
             * showAccounts();
             * break;
             */
            default:
                System.out.println("Invalid option.");
        }
        System.out.println();
        return true;
    }

    private void showMemberMenu() {
        System.out.println("=== Logged in as " + currentUser.getFullName() + " ===");
        System.out.printf("Current Balance: %.2f Php\n", currentUser.getBalance());
        System.out.println("1. Cash-in");
        System.out.println("2. Transfer");
        System.out.println("3. Log out");
        System.out.println("4. View Transaction History");
        System.out.println("5. Delete Account");        // code to be added.
        System.out.print("Choose an option: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                try {
                    transactionServices.cashIn(currentUser, scanner);
                } catch (IllegalArgumentException e) {
                    System.out.println("Cash-In failed! " + e.getMessage());
                }
                break;
            case "2":
                try {
                    transactionServices.transferToAnotherUser(currentUser, scanner);
                } catch (IllegalArgumentException e) {
                    System.out.println("Transfer failed! " + e.getMessage());
                }
                break;
            case "3":
                currentUser = null;
                System.out.println("Logged out.");
                break;
            case "4":
                transactionServices.viewTransactionHistory(currentUser);
                break;
            default:
                System.out.println("Invalid option.");
        }
        System.out.println();
    }
}