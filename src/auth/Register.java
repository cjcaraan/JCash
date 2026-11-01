package auth;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import model.User;

public class Register {
    private Map<String, User> users = new LinkedHashMap<>();
    private long nextUserId = 1000000001L;

    public User createAccount(String mobileNumber, String fullName, String pin) {

        long userId = nextUserId++;         // userID will be sequential

        if (mobileNumber == null || mobileNumber.isBlank()) {
            throw new IllegalArgumentException("Mobile Number cannot be empty.");
        }
        mobileNumber = mobileNumber.trim();

        if (users.containsKey(mobileNumber)) {
            throw new IllegalArgumentException("That mobile number is already registered.");
        }

        if (fullName == null || fullName.isBlank()) {
            throw new IllegalArgumentException("Full Name cannot be empty.");
        }

        if (pin == null || pin.length() < 8) {
            throw new IllegalArgumentException("pin must be at least 8 characters long.");
        }

        User account = new User(userId, mobileNumber, fullName, pin);
        users.put(fullName, account);
        return account;
    }

    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }

    
    public User login(String mobileNumber, String pin) {
        User user = (mobileNumber == null) ? null : users.get(mobileNumber.trim());

        if (user == null || !user.checkpin(pin)) {
            throw new IllegalArgumentException("Invalid Mobile Number or pin.");
        }
        return user;
    }
}