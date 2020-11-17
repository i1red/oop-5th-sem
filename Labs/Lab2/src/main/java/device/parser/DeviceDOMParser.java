package device.parser;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.transform.dom.DOMSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import device.Device;
import device.DeviceDetails;
import device.DeviceFamily;
import device.Port;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

import java.io.File;
import java.util.ArrayList;

public class DeviceDOMParser implements DeviceParser {
    private final DocumentBuilder documentBuilder;
    private final Validator validator;

    public DeviceDOMParser() throws ParserCreationException {
        this(null);
    }

    public DeviceDOMParser(String validationSchemaFilepath) throws ParserCreationException {
        try {
            this.validator = validationSchemaFilepath != null ?
                    SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                            .newSchema(new File(validationSchemaFilepath)).newValidator() : null;

            this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
        }
        catch (Exception e) {
            throw new ParserCreationException(e);
        }
    }

    @Override
    public ArrayList<Device> parseDevices(String filepath) throws DocumentParsingException {
        try {
            Document document = documentBuilder.parse(new File(filepath));
            document.getDocumentElement().normalize();

            if (this.validator != null) {
                this.validator.validate(new DOMSource(document));
            }

            var devices = new ArrayList<Device>();
            NodeList deviceNodes = document.getElementsByTagName("device");

            for (int i = 0; i < deviceNodes.getLength(); ++i) {
                devices.add(this.getDeviceFromElement((Element) deviceNodes.item(i)));
            }

            return devices;
        } catch (Exception e) {
            throw new DocumentParsingException(e);
        }
    }

    private DeviceDetails getDeviceDetailsFromElement(Element deviceDetailsElement) {
        boolean isPeripheral = Boolean.parseBoolean(
                deviceDetailsElement.getElementsByTagName("isPeripheral").item(0).getTextContent()
        );
        int energyConsumption = Integer.parseInt(
                deviceDetailsElement.getElementsByTagName("energyConsumption").item(0).getTextContent()
        );
        boolean hasCooler = Boolean.parseBoolean(
                deviceDetailsElement.getElementsByTagName("hasCooler").item(0).getTextContent()
        );

        DeviceFamily deviceFamily = DeviceFamily.valueOf(
                deviceDetailsElement.getElementsByTagName("deviceFamily").item(0).getTextContent()
        );

        var ports = new ArrayList<Port>();
        NodeList portNodes = deviceDetailsElement.getElementsByTagName("port");

        for (int i = 0; i < portNodes.getLength(); ++i) {
            ports.add(Port.valueOf(portNodes.item(i).getTextContent()));
        }

        return new DeviceDetails(isPeripheral, energyConsumption, hasCooler, deviceFamily, ports);
    }

    private Device getDeviceFromElement(Element deviceElement) {
        int id = Integer.parseInt(deviceElement.getAttribute("id"));

        String name = deviceElement.getElementsByTagName("name").item(0).getTextContent();

        String countryOfOrigin = deviceElement.getElementsByTagName("countryOfOrigin").item(0).getTextContent();

        int price = Integer.parseInt(
                deviceElement.getElementsByTagName("price").item(0).getTextContent()
        );

        DeviceDetails deviceDetails = getDeviceDetailsFromElement(
                (Element) deviceElement.getElementsByTagName("deviceDetails").item(0)
        );

        boolean isCritical = Boolean.parseBoolean(
                deviceElement.getElementsByTagName("isCritical").item(0).getTextContent()
        );

        return new Device(id, name, countryOfOrigin, price, deviceDetails, isCritical);
    }
}
