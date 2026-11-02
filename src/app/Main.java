package app;
import auth.Register;
import auth.Login;
import model.User;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static final Path FILE = Path.of("src", "database", "numbers.txt");
    static List<String> numbers = new ArrayList<>();

    private Scanner scanner = new Scanner(System.in);
    private Register register = new Register();
    private Login login = new Login(register);
    private User currentUser = null; // initially no one is logged in

    public static void main(String[] args) {
        loadNumbers();
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
                currentUser = login.handleLogin(scanner);
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
        User newUser = register.handleRegister(scanner);
        if (newUser != null) {
            numbers.add(newUser.getMobileNumber());
            saveNumbers();
        }
    }

    static void saveNumbers() {
        try {
            Files.createDirectories(FILE.getParent());          // creates database folder if for some reason it doesnt exist yet
            Files.write(FILE, numbers);
        }
        catch (IOException e) {
            System.out.print("Could not save file: " + e.getMessage());
        }
    }

    static void loadNumbers() {
        if (Files.exists(FILE)) {
            try {
                numbers = new ArrayList<>(Files.readAllLines(FILE));
            } 
            catch (IOException e) {
                System.out.println("Could not read file: " + e.getMessage());
            }
        }
    }

}