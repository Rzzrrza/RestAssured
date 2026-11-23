public class DeceasedRecord {
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