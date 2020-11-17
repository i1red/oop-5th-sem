public class Device {
    private final int id;
    private final String countryOfOrigin;
    private final int price;
    private final DeviceDetails deviceDetails;
    private final boolean isCritical;


    public Device(int id, String countryOfOrigin, int price, DeviceDetails deviceDetails, boolean isCritical) {
        this.id = id;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.deviceDetails = deviceDetails;
        this.isCritical = isCritical;
    }

    public int getId() {
        return id;
    }

    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }

    public int getPrice() {
        return price;
    }

    public DeviceDetails getDeviceDetails() {
        return deviceDetails;
    }

    public boolean isCritical() {
        return isCritical;
    }
}
