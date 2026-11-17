import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class FuneralService {
    protected String packageName;
    protected double basePrice;

    public FuneralService(String packageName, double basePrice) {
        this.packageName = packageName;
        this.basePrice = basePrice;
    }

    public String getPackageName() {
        return packageName;
    }

    public double getBasePrice() {
        return basePrice;
    }

    public abstract String displayDetails();

    public static void main(String[] args) {
        Funeralz app = new Funeralz();
        app.run();
    }
}

class BurialPackage extends FuneralService {
    private String casketType;
    private int viewingDays;
    private String extras;

    public BurialPackage(String packageName, double basePrice, String casketType, int viewingDays, String extras) {
        super(packageName, basePrice);
        this.casketType = casketType;
        this.viewingDays = viewingDays;
        this.extras = extras;
    }

    @Override
    public String displayDetails() {
        return String.format("%s - PHP %,.2f\n   Inclusions: %s Casket, %d-Day Viewing, %s",
                packageName, basePrice, casketType, viewingDays, extras);
    }
}

class PremiumMemorialPackage extends FuneralService {
    private String casketOrUrn;
    private int viewingDays;
    private String vipServices;

    public PremiumMemorialPackage(String packageName, double basePrice, String casketOrUrn, int viewingDays, String vipServices) {
        super(packageName, basePrice);
        this.casketOrUrn = casketOrUrn;
        this.viewingDays = viewingDays;
        this.vipServices = vipServices;
    }

    @Override
    public String displayDetails() {
        return String.format("%s - PHP %,.2f\n   VIP Inclusions: %s, %d-Day Family Suite Viewing, %s",
                packageName, basePrice, casketOrUrn, viewingDays, vipServices);
    }
}

class DeceasedRecord {
    private String recordId;
    private String fullName;
    private String dateOfDeath;
    private String schedule;
    private FuneralService funeralPackage;
    private String paymentStatus;
    private boolean documentsComplete;

    public DeceasedRecord(String recordId, String fullName, String dateOfDeath, String schedule, FuneralService funeralPackage) {
        this.recordId = recordId;
        this.fullName = fullName;
        this.dateOfDeath = dateOfDeath;
        this.schedule = schedule;
        this.funeralPackage = funeralPackage;
        this.paymentStatus = "Unpaid";
        this.documentsComplete = false;
    }

    public String getRecordId() {
        return recordId;
    }

    public String getFullName() {
        return fullName;
    }

    public String getSchedule() {
        return schedule;
    }

    public FuneralService getFuneralPackage() {
        return funeralPackage;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String status) {
        if (status.equals("Paid") || status.equals("Partial") || status.equals("Unpaid")) {
            this.paymentStatus = status;
        } else {
            System.out.println("Invalid status. Use Paid, Partial, or Unpaid.");
        }
    }

    public String getDocumentStatus() {
        return documentsComplete ? "Complete" : "Pending Death Certificate/Permits";
    }

    public void markDocumentsComplete() {
        this.documentsComplete = true;
    }

    public void displayRecord() {
        System.out.println("--------------------------------------------------");
        System.out.println("Record No. : " + recordId);
        System.out.println("Name       : " + fullName);
        System.out.println("Date Died  : " + dateOfDeath);
        System.out.printf("Package    : %s (PHP %,.2f)\n", funeralPackage.getPackageName(), funeralPackage.getBasePrice());
        System.out.println("Schedule   : " + schedule);
        System.out.println("Documents  : " + getDocumentStatus());
        System.out.println("Payment    : " + paymentStatus);
        System.out.println("--------------------------------------------------");
    }
}

class Funeralz {
    private List<FuneralService> packages;
    private List<DeceasedRecord> records;
    private List<String> availableSlots;
    private Map<String, String> customerAccounts;
    private Map<String, String> staffAccounts;
    private String currentUser;
    private String currentRole;
    private int nextIdNum;
    private Scanner scanner;

    public Funeralz() {
        packages = new ArrayList<>();
        packages.add(new BurialPackage("Normal Package", 45000, "Standard Wooden", 3, "Basic Flowers & Hearse"));
        packages.add(new BurialPackage("Standard Package", 75000, "Half-Glass Metal", 5, "Deluxe Flowers, Hearse & Tarpaulin"));
        packages.add(new PremiumMemorialPackage("Premium Package", 120000, "Imported Solid Hardwood / Brass Urn", 7, "Full Catering, Escort, Live Stream & Vigil Setup"));

        records = new ArrayList<>();
        nextIdNum = 1001;

        availableSlots = new ArrayList<>();
        availableSlots.add("Chapel A - Morning (8:00 AM - 12:00 PM)");
        availableSlots.add("Chapel A - Afternoon (1:00 PM - 5:00 PM)");
        availableSlots.add("Chapel B - Full Day Viewing");
        availableSlots.add("Memorial Garden / Crematorium - 10:00 AM");

        customerAccounts = new HashMap<>();
        staffAccounts = new HashMap<>();
        staffAccounts.put("admin", "admin123");

        currentUser = "Guest";
        currentRole = "Guest";
        scanner = new Scanner(System.in);
    }

    private String capitalize(String str) {
        if (str == null || str.isEmpty()) return str;
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public void showPackagesMenu() {
        while (true) {
            System.out.println("\n=== SERVICES AND PACKAGES OFFERED ===");
            for (int i = 0; i < packages.size(); i++) {
                System.out.println((i + 1) + ". " + packages.get(i).displayDetails() + "\n");
            }

            System.out.println("Options: [1] Avail a Package (Create Record)  [0] Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                createRecord();
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid option.");
            }
        }
    }

    public void createRecord() {
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

        System.out.println("\nAvailable Schedule Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + availableSlots.get(i));
        }
        System.out.println((availableSlots.size() + 1) + ". Custom Date & Time");
        System.out.print("Select Schedule Option: ");
        String slotChoice = scanner.nextLine().trim();

        String sched;
        try {
            int slotIdx = Integer.parseInt(slotChoice) - 1;
            if (slotIdx >= 0 && slotIdx < availableSlots.size()) {
                sched = availableSlots.remove(slotIdx);
            } else {
                System.out.print("Enter Viewing/Service Schedule: ");
                sched = scanner.nextLine().trim();
            }
        } catch (NumberFormatException e) {
            System.out.print("Enter Viewing/Service Schedule: ");
            sched = scanner.nextLine().trim();
        }

        String recordId = "REC-" + nextIdNum++;
        DeceasedRecord newRec = new DeceasedRecord(recordId, name, dod, sched, packages.get(pkgNum));

        if (currentRole.equals("Staff")) {
            System.out.print("Initial Payment Status (Paid / Partial / Unpaid): ");
            String pay = capitalize(scanner.nextLine().trim());
            newRec.setPaymentStatus(pay);

            System.out.print("Are documents complete? (yes/no): ");
            String docs = scanner.nextLine().trim().toLowerCase();
            if (docs.equals("yes")) {
                newRec.markDocumentsComplete();
            }
        }

        records.add(newRec);
        System.out.println("\nRecord successfully created! Assigned Record Number: " + recordId);
    }

    public void showSchedules() {
        while (true) {
            System.out.println("\n=== SCHEDULES ===");
            System.out.println("1. View Available Schedules");
            System.out.println("2. View Upcoming Services");
            System.out.println("0. Back to Main Menu");
            System.out.print("Enter choice: ");
            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {
                System.out.println("\n--- AVAILABLE SCHEDULES ---");
                if (availableSlots.isEmpty()) {
                    System.out.println("No open slots right now. Custom schedules can be arranged.");
                } else {
                    for (int i = 0; i < availableSlots.size(); i++) {
                        System.out.println((i + 1) + ". " + availableSlots.get(i));
                    }
                }
            } else if (choice.equals("2")) {
                System.out.println("\n--- UPCOMING FUNERAL & CREMATION SCHEDULES ---");
                if (records.isEmpty()) {
                    System.out.println("No schedules found.");
                } else {
                    for (DeceasedRecord rec : records) {
                        System.out.printf("[%s] %s | %s -> %s\n",
                                rec.getRecordId(), rec.getFullName(), rec.getFuneralPackage().getPackageName(), rec.getSchedule());
                    }
                }
            } else if (choice.equals("0")) {
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    public void searchRecords() {
        System.out.println("\n=== SEARCH DECEASED RECORDS ===");
        System.out.print("Enter Record Number (e.g., REC-1001) or Name: ");
        String keyword = scanner.nextLine().trim().toLowerCase();
        boolean found = false;

        for (DeceasedRecord rec : records) {
            if (rec.getRecordId().toLowerCase().contains(keyword) || rec.getFullName().toLowerCase().contains(keyword)) {
                rec.displayRecord();
                found = true;

                if (currentRole.equals("Staff")) {
                    System.out.print("Do you want to update Payment or Documents for this record? (y/n): ");
                    String update = scanner.nextLine().trim().toLowerCase();
                    if (update.equals("y")) {
                        System.out.print("Enter new payment status (Paid / Partial / Unpaid) or press Enter to skip: ");
                        String newPay = capitalize(scanner.nextLine().trim());
                        if (!newPay.isEmpty()) {
                            rec.setPaymentStatus(newPay);
                        }
                        System.out.print("Mark documents as complete? (y/n): ");
                        String docDone = scanner.nextLine().trim().toLowerCase();
                        if (docDone.equals("y")) {
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

    public void showLoginMenu() {
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

    public void run() {
        while (true) {
            System.out.println("\n====================================================");
            System.out.println(" LEAHNE'S FUNERAL SERVICE & MEMORIAL MANAGEMENT");
            System.out.println("====================================================");
            System.out.println("Current User: " + currentUser + " [" + currentRole + "]");
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
                    showPackagesMenu();
                    break;
                case "2":
                    showSchedules();
                    break;
                case "3":
                    searchRecords();
                    break;
                case "4":
                    showLoginMenu();
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