package device.parser;

import device.Device;
import device.DeviceFamily;
import device.Port;
import org.junit.Assert;
import org.junit.Test;

import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

@RunWith(Parameterized.class)
public class DeviceParserTest {
    private DeviceParser deviceParser;

    @Parameters
    public static List<DeviceParser> createDeviceParsers() throws ParserCreationException {
        return Arrays.asList(new DeviceDOMParser("src/main/resources/devicesSchema.xsd"));
    }

    public DeviceParserTest(DeviceParser deviceParser) {
        this.deviceParser = deviceParser;
    }

    @Test
    public void parseValidXml() throws DocumentParsingException {
        Device device = this.deviceParser.parseDevices("src/test/resources/validInput.xml").get(0);

        Assert.assertEquals(0, device.getId());
        Assert.assertEquals("name", device.getName());
        Assert.assertEquals("countryOfOrigin", device.getCountryOfOrigin());
        Assert.assertEquals(1, device.getPrice());
        Assert.assertFalse(device.isCritical());


        Assert.assertTrue(device.getDeviceDetails().isPeripheral());
        Assert.assertTrue(device.getDeviceDetails().hasCooler());
        Assert.assertEquals(0, device.getDeviceDetails().getEnergyConsumption());
        Assert.assertEquals(DeviceFamily.IO, device.getDeviceDetails().getDeviceFamily());
        Assert.assertEquals(Port.HDMI, device.getDeviceDetails().getPorts().get(0));
    }

    @Test(expected = DocumentParsingException.class)
    public void parseInvalidXml() throws DocumentParsingException {
        this.deviceParser.parseDevices("src/test/resources/invalidInput.xml");
        //this.deviceParser.parseDevices("src/test/resources/devices.xml");
    }
}