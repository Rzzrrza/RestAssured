public class BurialPackage extends FuneralService {
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