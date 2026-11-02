package auth;

import model.User;
import java.util.Scanner;

public class Login {
    private final Register register;

    public Login(Register register) {
        this.register = register;
    }

    // Handler: returns the logged-in User, or null if login failed
    public User handleLogin(Scanner scanner) {
        System.out.print("Enter a mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter a pin: ");
        String pin = scanner.nextLine();

        try {
            User user = login(mobileNumber, pin);
            System.out.println("Welcome back, " + user.getFullName() + "!");
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed! " + e.getMessage());
            return null;
        }
    }

    // login logic
    public User login(String mobileNumber, String pin) {
        User user = register.findByMobile(mobileNumber);

        if (user == null || !user.checkpin(pin)) {
            throw new IllegalArgumentException("Invalid mobile number or PIN.");
        }
        return user;
    }
}