package util;

import model.User;
import dao.UserDao;
import java.util.Scanner;

public class ValidationUtil {
    private final UserDao userDao = new UserDao();

    public boolean isValidMobileNumber(String mobileNumber) {

        if (mobileNumber == null || mobileNumber.isEmpty()) {
            throw new IllegalArgumentException("Mobile Number field cannot be empty.");
        }

        int numberLength = mobileNumber.length();
        if (numberLength != 11) {
            throw new IllegalArgumentException("Mobile Number must be exactly 11 digits");
        }

        try {
            Integer.parseInt(mobileNumber);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidPin(String pin) {
        int pinLength = pin.length();

        if (pinLength != 8) {
            return false;
        }

        try {
            Integer.parseInt(pin);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidUser(User userToCheck) {
        // add code to check the database;
        return false;
    }

    public String mobileNumberValidator(Scanner scanner) {
        boolean isValid = false;
        // lets say allow user up to n times to try logging in, goes back to guest menu
        // after failing n times.
        int counterMobileNumberValidator = 0;
        String mobileNumber;

        do {
            System.out.print("Enter a mobile number: ");
            mobileNumber = scanner.nextLine();
            counterMobileNumberValidator++;
            try {

                isValidMobileNumber(mobileNumber);
                if (userDao.isValidMobileNumber(mobileNumber)) {
                    return mobileNumber;
                } else {
                    System.out.println("This Mobile Number is not registered.");
                }

            } catch (IllegalArgumentException e) {
                System.out.println("Login Failed! " + e.getMessage());
                return null;
            }
        } while (!isValid && counterMobileNumberValidator < 5); // we will add "Back" option once gui is implemented.
        if (!isValid) {
            return null;
        }
        System.out.println("Something unexpected went wrong.");
        return null;
    }
}
