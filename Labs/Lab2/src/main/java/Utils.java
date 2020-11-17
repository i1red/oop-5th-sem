import device.Device;
import device.DeviceDetails;

import java.util.stream.Collectors;

public class Utils {
    public static void printDevice(Device device) {
        System.out.printf("DEVICE (ID=%d):\n\n", device.getId());

        System.out.printf("\t%-20s: %s\n", "country of origin", device.getCountryOfOrigin());
        System.out.printf("\t%-20s: %d\n", "price", device.getPrice());
        System.out.printf("\t%-20s: %s\n", "is critical", device.isCritical() ? "yes" : "no");

        DeviceDetails deviceDetails = device.getDeviceDetails();

        System.out.println("\n\t\t\tdetails");

        System.out.printf("\t%-20s: %s\n", "is peripheral", deviceDetails.isPeripheral() ? "yes" : "no");
        System.out.printf("\t%-20s: %d\n", "energy consumption", deviceDetails.getEnergyConsumption());
        System.out.printf("\t%-20s: %s\n", "has cooler", deviceDetails.hasCooler() ? "yes" : "no");
        System.out.printf("\t%-20s: %s\n", "device family", deviceDetails.getDeviceFamily());
        System.out.printf("\t%-20s: %s\n", "ports",
                deviceDetails.getPorts().stream().map(Enum::toString).collect(Collectors.joining(", ")));
    }
}
