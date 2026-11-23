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
}