package auth;

import model.User;
import java.util.Scanner;
import dao.UserDao;

public class Login {
    private final UserDao userDao = new UserDao();

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
        // get user using userDao
        User user = (mobileNumber == null) ? null : userDao.findByMobile(mobileNumber.trim());
        if (user == null || !user.checkpin(pin)) {
            throw new IllegalArgumentException("Invalid mobile number or PIN.");
        }
        return user;
    }
}