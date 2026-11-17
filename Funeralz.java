import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

abstract class FuneralService {
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
}

class TraditionalBurial extends FuneralService {
    private String casketType;
    private int days;

    public TraditionalBurial(String packageName, double basePrice, String casketType, int days) {
        super(packageName, basePrice);
        this.casketType = casketType;
        this.days = days;
    }

    @Override
    public String displayDetails() {
        return String.format("[Traditional Burial] %s - PHP %,.2f\n   Inclusions: %s Casket, %d-Day Viewing, Hearse & Flowers",
                packageName, basePrice, casketType, days);
    }
}

class CremationService extends FuneralService {
    private String urnType;
    private boolean includesChapel;

    public CremationService(String packageName, double basePrice, String urnType, boolean includesChapel) {
        super(packageName, basePrice);
        this.urnType = urnType;
        this.includesChapel = includesChapel;
    }

    @Override
    public String displayDetails() {
        String chapelInfo = includesChapel ? "With 1-Day Chapel Viewing" : "Direct Cremation (No Viewing)";
        return String.format("[Cremation] %s - PHP %,.2f\n   Inclusions: %s Urn, Cremation Process, %s",
                packageName, basePrice, urnType, chapelInfo);
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

public class Funeralz {
    private List<FuneralService> packages;
    private List<DeceasedRecord> records;
    private int nextIdNum;
    private Scanner scanner;

    public Funeralz() {
        packages = new ArrayList<>();
        packages.add(new TraditionalBurial("St. Peter Basic Wood", 45000, "Standard Wooden", 3));
        packages.add(new TraditionalBurial("St. Jude Deluxe Metal", 95000, "Imported Metal", 5));
        packages.add(new CremationService("Direct Cremation", 35000, "Standard Marble", false));
        packages.add(new CremationService("Memorial Cremation", 65000, "Premium Brass", true));

        records = new ArrayList<>();
        nextIdNum = 1001;
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
            System.out.print("Select Package Number (1-4): ");
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
        String name = scanner.nextLine();

        System.out.print("Enter Date of Death (YYYY-MM-DD): ");
        String dod = scanner.nextLine();

        System.out.print("Enter Viewing/Service Schedule: ");
        String sched = scanner.nextLine();

        String recordId = "REC-" + nextIdNum;
        nextIdNum++;

        DeceasedRecord newRec = new DeceasedRecord(recordId, name, dod, sched, packages.get(pkgNum));

        System.out.print("Initial Payment Status (Paid / Partial / Unpaid): ");
        String pay = capitalize(scanner.nextLine().trim());
        newRec.setPaymentStatus(pay);

        System.out.print("Are documents complete? (yes/no): ");
        String docs = scanner.nextLine().trim().toLowerCase();
        if (docs.equals("yes")) {
            newRec.markDocumentsComplete();
        }

        records.add(newRec);
        System.out.println("\nRecord successfully created! Assigned Record Number: " + recordId);
    }

    public void showSchedules() {
        System.out.println("\n=== UPCOMING FUNERAL & CREMATION SCHEDULES ===");
        if (records.isEmpty()) {
            System.out.println("No schedules found.");
            return;
        }

        for (DeceasedRecord rec : records) {
            System.out.printf("[%s] %s | %s -> %s\n",
                    rec.getRecordId(), rec.getFullName(), rec.getFuneralPackage().getPackageName(), rec.getSchedule());
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

                System.out.print("Do you want to update Payment or Documents for this record? (y/n): ");
                String update = scanner.nextLine().trim().toLowerCase();
                if (update.equals("y")) {
                    System.out.print("Enter new payment status (Paid / Partial / Unpaid) or press Enter to skip: ");
                    String newPay = scanner.nextLine().trim();
                    if (!newPay.isEmpty()) {
                        rec.setPaymentStatus(capitalize(newPay));
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

        if (!found) {
            System.out.println("No matching record found.");
        }
    }

    public void run() {
        while (true) {
            System.out.println("\n===================================================");
            System.out.println(" LEAHNE'S FUNERAL SERVICE & MEMORIAL MANAGEMENT");
            System.out.println("===================================================");
            System.out.println("1. Services and Packages Offered");
            System.out.println("2. Schedules");
            System.out.println("3. Search");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

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
                    System.out.println("Exiting system. Goodbye!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter 1 to 4.");
            }
        }
    }

    public static void main(String[] args) {
        Funeralz app = new Funeralz();
        app.run();
    }
}