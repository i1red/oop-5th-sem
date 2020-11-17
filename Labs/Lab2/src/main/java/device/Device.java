package device;

public class Device {
    private final int id;
    private final String name;
    private final String countryOfOrigin;
    private final int price;
    private final DeviceDetails deviceDetails;
    private final boolean isCritical;

    public Device(int id, String name, String countryOfOrigin, int price,
                  DeviceDetails deviceDetails, boolean isCritical) {
        this.id = id;
        this.name = name;
        this.countryOfOrigin = countryOfOrigin;
        this.price = price;
        this.deviceDetails = deviceDetails;
        this.isCritical = isCritical;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
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
