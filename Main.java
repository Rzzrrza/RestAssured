import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        AuthManager auth = new AuthManager();
        ScheduleManager scheduleManager = new ScheduleManager();
        PackageManager packageManager = new PackageManager(scheduleManager, auth);

        while (true) {
            System.out.println("\n====================================================");
            System.out.println(" LEAHNE'S FUNERAL SERVICE & MEMORIAL MANAGEMENT");
            System.out.println("====================================================");
            System.out.println("Current User: " + auth.getCurrentUser() + " [" + auth.getCurrentRole() + "]");
            System.out.println("----------------------------------------------------");
            System.out.println("1. Services and Packages Offered");
            System.out.println("2. Schedules");
            System.out.println("3. Search");
            System.out.println("4. Login (Customer / Staff)");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    packageManager.showPackagesMenu(scanner);
                    break;
                case "2":
                    scheduleManager.showSchedulesMenu(scanner);
                    break;
                case "3":
                    packageManager.searchRecords(scanner);
                    break;
                case "4":
                    auth.showLoginMenu(scanner);
                    break;
                case "5":
                    System.out.println("Exiting system. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 to 5.");
            }
        }
    }
}