package auth;

import model.User;
import java.util.Scanner;
import dao.UserDao;
import util.ValidationUtil;

public class Login {
    private ValidationUtil validationUtil = new ValidationUtil();
    private UserDao userDao = new UserDao();

    // Handler: returns the logged-in User, or null if login failed
    public User handleLogin(Scanner scanner) {

        String accountMobileNumber = validationUtil.mobileNumberValidator(scanner);

        if (accountMobileNumber == null) {
            return null; // return null after a couple of failed attempts
        }

        try {
            User user = login(scanner, accountMobileNumber);
            System.out.println("Welcome back, " + user.getFullName() + "!");
            return user;
        } catch (IllegalArgumentException e) {
            System.out.println("Login failed! " + e.getMessage());
            return null;
        }
    }

    // login logic
    public User login(Scanner scanner, String mobileNumber) {
        int attempts = 3;
        String correctPin = userDao.getCorrectPin(mobileNumber);
        User user = null;

        do {
            System.out.print("Enter pin: ");
            String pin = scanner.nextLine();
            attempts--;

            try {
                pinChecker(pin, correctPin);
                user = (mobileNumber == null) ? null : userDao.findByMobile(mobileNumber.trim());
                System.out.println("Welcome back, " + user.getFullName() + "!");
                return user;
            } catch (IllegalArgumentException e) {
                System.out.println(e.getMessage() + " You have " + attempts + " attempts left.");
            }
        } while (attempts > 0);

        if (user == null) {
            throw new IllegalArgumentException("Too many failed login attempts. Please try again later.");
        }
        return null;
    }

    private void pinChecker(String pinAttempt, String correctPinToCompare) {
        if (pinAttempt == null || pinAttempt.length() != 8) {
            throw new IllegalArgumentException("Please enter a valid PIN.");
        }

        if (!pinAttempt.equals(correctPinToCompare)) {
            throw new IllegalArgumentException("Wrong PIN, please try again.");
        }

    }
}