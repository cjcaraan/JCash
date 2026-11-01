package app;
import auth.Register;
import model.User;

import java.util.List;
import java.util.Scanner;

public class Main {

    private Scanner scanner = new Scanner(System.in);
    private Register register = new Register();
    private User currentUser = null; // initially no one is logged in

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        boolean running = true;
        while (running) {
            if (currentUser == null) {
                running = showGuestMenu();
            }
            else {
                //showMemberMenu();
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
                handleRegister();
                break;
            case "2":
                handleLogin();
                break;
            case "3":
                return false;
/*             case "4":
                showAccounts();
                break; */
            default:
                System.out.println("Invalid option.");
        }
        System.out.println();
        return true;
    }

    // debug code to be removed later (admin access to show all accounts registered at the moment)
    private void showAccounts() {
        List<User> accounts = register.getAllUsers();

        if (accounts.isEmpty()) {
            System.out.println("No accounts registered yet.");
            return;
        }

        System.out.println("Registered accounts (" + accounts.size() + "):");
        for (User user : accounts) {
            System.out.println("ID: " + user.getUserId()
                    + " | Name: " + user.getFullName()
                    + " | Mobile: " + user.getMobileNumber());
        }
    }
    

    private void handleRegister() {
        System.out.print("Enter a mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter a pin: ");
        String pin = scanner.nextLine();

        try {
            User account = register.createAccount(mobileNumber, fullName, pin);
            System.out.println("Account created for " + account.getFullName() + "!");
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
        }
    }

    private void handleLogin() {
        System.out.print("Enter a mobileNumber: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter a pin: ");
        String pin = scanner.nextLine();

        try {
            currentUser = register.login(mobileNumber, pin);
            System.out.println("Welcome back, " + currentUser.getMobileNumber() + "!");
        }
        catch (IllegalArgumentException e) {
            System.out.println("Login Failed! " + e.getMessage());
        }
    }

}