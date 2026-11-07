package util;

import model.User;

public class ValidationUtil {

    public boolean isValidMobileNumber(String mobileNumber) {
        int numberLength = mobileNumber.length();
        if (numberLength != 11) {
            // throw new IllegalArgumentException("Mobile Number must be exactly 11 digits");
            return false;
        }

        if (mobileNumber == null || mobileNumber.isEmpty()) {
            return false;
        }

        try {
            Integer.parseInt(mobileNumber);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    public boolean isValidPin(String pin) {
        int pinLength = pin.length();

        if (pinLength != 6) {
            return false;
        }        
        
        try {
            Integer.parseInt(pin);
            return true;
        }
        catch (NumberFormatException e) {
            return false;
        }
    }

    

    public boolean isValidUser(User userToCheck) {
        // add code to check the database;
        return false;
    }

}
