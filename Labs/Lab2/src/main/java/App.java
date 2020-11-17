import javax.xml.parsers.ParserConfigurationException;
import java.util.List;

public class App {
    public static void main(String[] args) {
        try {
            DeviceParser deviceParser = new DeviceDOMParser("src/main/resources/devicesSchema.xsd");
            List<Device> devices = deviceParser.parseDevices("src/main/resources/devices.xml");

            for (var device: devices) {
                Utils.printDevice(device);
                System.out.println();
                System.out.println();
            }
        } catch (FailedToParseException | ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
