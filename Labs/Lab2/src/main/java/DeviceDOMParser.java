import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class DeviceDOMParser implements DeviceParser {
    private final DocumentBuilder documentBuilder;

    public DeviceDOMParser() throws ParserConfigurationException {
        this(null);
    }
    public DeviceDOMParser(String validationSchemaFilepath) throws ParserConfigurationException {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();

        if (validationSchemaFilepath != null) {
            documentBuilderFactory.setValidating(true);
            documentBuilderFactory.setAttribute(
                    "http://java.sun.com/xml/jaxp/properties/schemaLanguage",
                    "http://www.w3.org/2001/XMLSchema"
            );
            documentBuilderFactory.setAttribute(
                    "http://java.sun.com/xml/jaxp/properties/schemaSource",
                    new File(validationSchemaFilepath)
            );
        }

        this.documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
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


        var ports = new ArrayList<Port>();
        NodeList portNodes = deviceDetailsElement.getElementsByTagName("port");

        for (int i = 0; i < portNodes.getLength(); ++i) {
            ports.add(Port.valueOf(portNodes.item(i).getTextContent()));
        }

        return new DeviceDetails(isPeripheral, energyConsumption, hasCooler, ports);
    }

    private Device getDeviceFromElement(Element deviceElement) {
        int id = Integer.parseInt(deviceElement.getAttribute("id"));

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

        return new Device(id, countryOfOrigin, price, deviceDetails, isCritical);
    }

    @Override
    public ArrayList<Device> parseDevices(String filepath) throws FailedToParseException {
        try {
            Document document = documentBuilder.parse(new File(filepath));
            document.getDocumentElement().normalize();

            var devices = new ArrayList<Device>();
            NodeList deviceNodes = document.getElementsByTagName("device");

            for (int i = 0; i < deviceNodes.getLength(); ++i) {
                devices.add(this.getDeviceFromElement((Element) deviceNodes.item(i)));
            }

            return devices;
        } catch (Exception e) {
            throw new FailedToParseException(e);
        }
    }
}
