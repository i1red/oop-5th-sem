package device.parser;

import device.Device;

import java.util.ArrayList;

public interface DeviceParser {
    ArrayList<Device> parseDevices(String filepath) throws DocumentParsingException;
}
