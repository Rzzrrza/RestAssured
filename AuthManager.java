import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class AuthManager {
    private Map<String, String> customerAccounts;
    private Map<String, String> staffAccounts;
    private String currentUser;
    private String currentRole;

    public AuthManager() {
        customerAccounts = new HashMap<>();
        staffAccounts = new HashMap<>();
        staffAccounts.put("admin", "admin123");

        currentUser = "Guest";
        currentRole = "Guest";
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public boolean isStaff() {
        return currentRole.equals("Staff");
    }

    public void showLoginMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== LOGIN (CUSTOMER / STAFF) ===");
            System.out.println("1. Customer Login");
            System.out.println("2. Staff Login");
            System.out.println("3. Register Account");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.print("Customer Username: ");
                String user = scanner.nextLine().trim();
                System.out.print("Password: ");
                String pass = scanner.nextLine().trim();

                if (customerAccounts.containsKey(user) && customerAccounts.get(user).equals(pass)) {
                    currentUser = user;
                    currentRole = "Customer";
                    System.out.println("Logged in as Customer: " + currentUser);
                    break;
                } else {
                    System.out.println("Invalid Customer credentials. Register an account first if you don't have one.");
                }
            } else if (choice.equals("2")) {
                System.out.print("Staff Username (default: admin): ");
                String user = scanner.nextLine().trim();
                System.out.print("Password (default: admin123): ");
                String pass = scanner.nextLine().trim();

                if (staffAccounts.containsKey(user) && staffAccounts.get(user).equals(pass)) {
                    currentUser = user;
                    currentRole = "Staff";
                    System.out.println("Logged in as Staff: " + currentUser + " (Record editing unlocked)");
                    break;
                } else {
                    System.out.println("Invalid Staff credentials.");
                }
            } else if (choice.equals("3")) {
                System.out.print("Register as [1] Customer or [2] Staff: ");
                String roleChoice = scanner.nextLine().trim();
                System.out.print("Create Username: ");
                String newUser = scanner.nextLine().trim();
                System.out.print("Create Password: ");
                String newPass = scanner.nextLine().trim();

                if (roleChoice.equals("1")) {
                    customerAccounts.put(newUser, newPass);
                    System.out.println("Customer account registered! You can now log in.");
                } else if (roleChoice.equals("2")) {
                    staffAccounts.put(newUser, newPass);
                    System.out.println("Staff account registered! You can now log in.");
                } else {
                    System.out.println("Invalid role selection.");
                }
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }
}