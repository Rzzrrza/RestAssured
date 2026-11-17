from abc import ABC, abstractmethod


class FuneralService(ABC):
    def __init__(self, package_name, base_price):
        self.package_name = package_name
        self.base_price = base_price

    @abstractmethod
    def display_details(self):
        pass


class TraditionalBurial(FuneralService):
    def __init__(self, package_name, base_price, casket_type, days):
        super().__init__(package_name, base_price)
        self.casket_type = casket_type
        self.days = days

    def display_details(self):
        return (f"[Traditional Burial] {self.package_name} - PHP {self.base_price:,.2f}\n"
                f"   Inclusions: {self.casket_type} Casket, {self.days}-Day Viewing, Hearse & Flowers")


class CremationService(FuneralService):
    def __init__(self, package_name, base_price, urn_type, includes_chapel):
        super().__init__(package_name, base_price)
        self.urn_type = urn_type
        self.includes_chapel = includes_chapel

    def display_details(self):
        chapel_info = "With 1-Day Chapel Viewing" if self.includes_chapel else "Direct Cremation (No Viewing)"
        return (f"[Cremation] {self.package_name} - PHP {self.base_price:,.2f}\n"
                f"   Inclusions: {self.urn_type} Urn, Cremation Process, {chapel_info}")


class DeceasedRecord:
    def __init__(self, record_id, full_name, date_of_death, schedule, package: FuneralService):
        self.__record_id = record_id
        self.full_name = full_name
        self.date_of_death = date_of_death
        self.schedule = schedule
        self.package = package
        self.__payment_status = "Unpaid"
        self.__documents_complete = False

    def get_record_id(self):
        return self.__record_id

    def get_payment_status(self):
        return self.__payment_status

    def set_payment_status(self, status):
        if status in ["Paid", "Partial", "Unpaid"]:
            self.__payment_status = status
        else:
            print("Invalid status. Use Paid, Partial, or Unpaid.")

    def get_document_status(self):
        return "Complete" if self.__documents_complete else "Pending Death Certificate/Permits"

    def mark_documents_complete(self):
        self.__documents_complete = True

    def display_record(self):
        print("-" * 50)
        print(f"Record No. : {self.__record_id}")
        print(f"Name       : {self.full_name}")
        print(f"Date Died  : {self.date_of_death}")
        print(f"Package    : {self.package.package_name} (PHP {self.package.base_price:,.2f})")
        print(f"Schedule   : {self.schedule}")
        print(f"Documents  : {self.get_document_status()}")
        print(f"Payment    : {self.__payment_status}")
        print("-" * 50)


class MemorialManagementSystem:
    def __init__(self):
        self.packages = [
            TraditionalBurial("St. Peter Basic Wood", 45000, "Standard Wooden", 3),
            TraditionalBurial("St. Jude Deluxe Metal", 95000, "Imported Metal", 5),
            CremationService("Direct Cremation", 35000, "Standard Marble", False),
            CremationService("Memorial Cremation", 65000, "Premium Brass", True)
        ]

        self.records = []
        self.next_id_num = 1001

    def show_packages_menu(self):
        while True:
            print("\n=== SERVICES AND PACKAGES OFFERED ===")
            for idx, pkg in enumerate(self.packages, 1):
                print(f"{idx}. {pkg.display_details()}\n")

            print("Options: [1] Avail a Package (Create Record)  [0] Back to Main Menu")
            choice = input("Enter choice: ")

            if choice == "1":
                self.create_record()
            elif choice == "0":
                break
            else:
                print("Invalid option.")

    def create_record(self):
        try:
            pkg_num = int(input("Select Package Number (1-4): ")) - 1
            if pkg_num < 0 or pkg_num >= len(self.packages):
                print("Invalid package number.")
                return
        except ValueError:
            print("Please enter a valid number.")
            return

        name = input("Enter Deceased Full Name: ")
        dod = input("Enter Date of Death (YYYY-MM-DD): ")
        sched = input("Enter Viewing/Service Schedule: ")

        record_id = f"REC-{self.next_id_num}"
        self.next_id_num += 1

        new_rec = DeceasedRecord(record_id, name, dod, sched, self.packages[pkg_num])
        
        pay = input("Initial Payment Status (Paid / Partial / Unpaid): ").capitalize()
        new_rec.set_payment_status(pay)

        docs = input("Are documents complete? (yes/no): ").lower()
        if docs == "yes":
            new_rec.mark_documents_complete()

        self.records.append(new_rec)
        print(f"\nRecord successfully created! Assigned Record Number: {record_id}")

    def show_schedules(self):
        print("\n=== UPCOMING FUNERAL & CREMATION SCHEDULES ===")
        if not self.records:
            print("No schedules found.")
            return

        for rec in self.records:
            print(f"[{rec.get_record_id()}] {rec.full_name} | {rec.package.package_name} -> {rec.schedule}")

    def search_records(self):
        print("\n=== SEARCH DECEASED RECORDS ===")
        keyword = input("Enter Record Number (e.g., REC-1001) or Name: ").strip().lower()
        found = False

        for rec in self.records:
            if keyword in rec.get_record_id().lower() or keyword in rec.full_name.lower():
                rec.display_record()
                found = True
                
                update = input("Do you want to update Payment or Documents for this record? (y/n): ").lower()
                if update == "y":
                    new_pay = input("Enter new payment status (Paid / Partial / Unpaid) or press Enter to skip: ").capitalize()
                    if new_pay:
                        rec.set_payment_status(new_pay)
                    doc_done = input("Mark documents as complete? (y/n): ").lower()
                    if doc_done == "y":
                        rec.mark_documents_complete()
                    print("Record updated!")

        if not found:
            print("No matching record found.")

    def run(self):
        while True:
            print("\n==========================================")
            print(" FUNERAL SERVICE & MEMORIAL MANAGEMENT")
            print("==========================================")
            print("1. Services and Packages Offered")
            print("2. Schedules")
            print("3. Search")
            print("4. Exit")
            
            choice = input("Select an option (1-4): ")

            if choice == "1":
                self.show_packages_menu()
            elif choice == "2":
                self.show_schedules()
            elif choice == "3":
                self.search_records()
            elif choice == "4":
                print("Exiting system. Goodbye!")
                break
            else:
                print("Invalid choice. Please enter 1 to 4.")


if __name__ == "__main__":
    app = MemorialManagementSystem()
    app.run()