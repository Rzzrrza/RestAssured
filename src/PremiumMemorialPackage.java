public class PremiumMemorialPackage extends FuneralService {
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