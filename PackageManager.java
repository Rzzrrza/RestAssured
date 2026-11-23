import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PackageManager {
    private List<FuneralService> packages;
    private List<DeceasedRecord> records;
    private ScheduleManager scheduleManager;
    private AuthManager auth;
    private int nextIdNum;

    public PackageManager(ScheduleManager scheduleManager, AuthManager auth) {
        this.scheduleManager = scheduleManager;
        this.auth = auth;
        this.records = new ArrayList<>();
        this.nextIdNum = 1001;

        packages = new ArrayList<>();
        packages.add(new BurialPackage("Normal Package", 45000, "Standard Wooden", 3, "Basic Flowers & Hearse"));
        packages.add(new BurialPackage("Standard Package", 75000, "Half-Glass Metal", 5, "Deluxe Flowers, Hearse & Tarpaulin"));
        packages.add(new PremiumMemorialPackage("Premium Package", 120000, "Imported Solid Hardwood / Brass Urn", 7, "Full Catering, Escort, Live Stream & Vigil Setup"));
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void showPackagesMenu(Scanner scanner) {
        while (true) {
            System.out.println("\n=== SERVICES AND PACKAGES OFFERED ===");
            for (int i = 0; i < packages.size(); i++) {
                System.out.println((i + 1) + ". " + packages.get(i).displayDetails() + "\n");
            }

            System.out.println("Options: [1] Avail a Package (Create Record)  [0] Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                createRecord(scanner);
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    private void createRecord(Scanner scanner) {
        int pkgNum;
        try {
            System.out.print("Select Package Number (1-3): ");
            pkgNum = Integer.parseInt(scanner.nextLine().trim()) - 1;
            if (pkgNum < 0 || pkgNum >= packages.size()) {
                System.out.println("Invalid package number.");
                return;
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
            return;
        }

        System.out.print("Enter Deceased Full Name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter Date of Death (YYYY-MM-DD): ");
        String dod = scanner.nextLine().trim();

        String sched = scheduleManager.selectOrCreateSchedule(scanner);
        String recordId = "REC-" + nextIdNum++;

        DeceasedRecord newRec = new DeceasedRecord(recordId, name, dod, sched, packages.get(pkgNum));

        if (auth.isStaff()) {
            System.out.print("Initial Payment Status (Paid / Partial / Unpaid): ");
            newRec.setPaymentStatus(capitalize(scanner.nextLine().trim()));

            System.out.print("Are documents complete? (yes/no): ");
            if (scanner.nextLine().trim().equalsIgnoreCase("yes")) {
                newRec.markDocumentsComplete();
            }
        }

        records.add(newRec);
        scheduleManager.addBookedRecord(newRec);
        System.out.println("\nRecord successfully created! Assigned Record Number: " + recordId);
    }

    public void searchRecords(Scanner scanner) {
        System.out.println("\n=== SEARCH DECEASED RECORDS ===");
        System.out.print("Enter Record Number (e.g., REC-1001) or Name: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        boolean found = false;

        for (DeceasedRecord rec : records) {
            if (rec.getRecordId().toLowerCase().contains(keyword) || rec.getFullName().toLowerCase().contains(keyword)) {
                rec.displayRecord();
                found = true;

                if (auth.isStaff()) {
                    System.out.print("Do you want to update Payment or Documents for this record? (y/n): ");
                    if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                        System.out.print("Enter new payment status (Paid / Partial / Unpaid) or press Enter to skip: ");
                        String newPay = capitalize(scanner.nextLine().trim());
                        if (!newPay.isEmpty()) {
                            rec.setPaymentStatus(newPay);
                        }
                        System.out.print("Mark documents as complete? (y/n): ");
                        if (scanner.nextLine().trim().equalsIgnoreCase("y")) {
                            rec.markDocumentsComplete();
                        }
                        System.out.println("Record updated!");
                    }
                }
            }
        }

        if (!found) {
            System.out.println("No matching record found.");
        }
    }
}