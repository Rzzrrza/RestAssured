import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ScheduleManager {
    private List<String> availableSlots;
    private List<DeceasedRecord> upcomingServices;

    public ScheduleManager() {
        availableSlots = new ArrayList<>();
        availableSlots.add("Chapel A - Morning (8:00 AM - 12:00 PM)");
        availableSlots.add("Chapel A - Afternoon (1:00 PM - 5:00 PM)");
        availableSlots.add("Chapel B - Full Day Viewing");
        availableSlots.add("Memorial Garden / Crematorium - 10:00 AM");

        upcomingServices = new ArrayList<>();
    }

    public void addBookedRecord(DeceasedRecord record) {
        upcomingServices.add(record);
    }

    public String selectOrCreateSchedule(Scanner scanner) {
        System.out.println("\nAvailable Schedule Slots:");
        for (int i = 0; i < availableSlots.size(); i++) {
            System.out.println((i + 1) + ". " + availableSlots.get(i));
        }
        System.out.println((availableSlots.size() + 1) + ". Custom Date & Time");
        System.out.print("Select Schedule Option: ");
        String slotChoice = scanner.nextLine().trim();

        try {
            int slotIdx = Integer.parseInt(slotChoice) - 1;
            if (slotIdx >= 0 && slotIdx < availableSlots.size()) {
                return availableSlots.remove(slotIdx);
            }
        } catch (NumberFormatException ignored) {
        }

        System.out.print("Enter Viewing/Service Schedule: ");
        return scanner.nextLine().trim();
    }

    public void showSchedulesMenu(Scanner scanner) {
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
                if (upcomingServices.isEmpty()) {
                    System.out.println("No schedules found.");
                } else {
                    for (DeceasedRecord rec : upcomingServices) {
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
}