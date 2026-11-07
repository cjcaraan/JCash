package auth;


import java.util.Scanner;
import model.User;
import dao.UserDao;

public class Register {
    // private long nextUserId = 1000000001L;                   // old userId for the hashmap
    private UserDao userDao = new UserDao();

    // Handler: asks for input. Returns the new User, or null if it failed
    public User handleRegister(Scanner scanner) {
        System.out.print("Enter a mobile number: ");
        String mobileNumber = scanner.nextLine();

        System.out.print("Enter your full name: ");
        String fullName = scanner.nextLine();

        System.out.print("Enter a pin: ");
        String pin = scanner.nextLine();

        try {
            User account = createAccount(mobileNumber, fullName, pin);
            System.out.println("Account created for " + account.getFullName() + "!");
            return account;
        } catch (IllegalArgumentException e) {
            System.out.println("Registration failed: " + e.getMessage());
            return null;
        }
    }

    // registration logic
    public User createAccount(String mobileNumber, String fullName, String pin) {
        if (mobileNumber == null || mobileNumber.isBlank()) {
            throw new IllegalArgumentException("Mobile number cannot be empty.");
        }
        mobileNumber = mobileNumber.trim();

        // checkers
        if (userDao.findByMobile(mobileNumber) != null) {
            throw new IllegalArgumentException("This mobile number is already registered.");
        }
        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full name cannot be empty.");
        }
        if (pin == null || !pin.matches("\\d{8}")) {            // lets say the pin configuration is 8 digits
            throw new IllegalArgumentException("PIN must be exactly 8 digits.");
        }

        long userId = userDao.nextUserId();     // only used up once everything is valid
        User account = new User(userId, mobileNumber, fullName.trim(), pin, 0);
        userDao.insert(account);
        return account;
    }

    // Lets Login look up an account without seeing the map itself
    //public User findByMobile(String mobileNumber) {
        //if (mobileNumber == null) {
            //return null;
        //}
        //return users.get(mobileNumber.trim());
    //}
}