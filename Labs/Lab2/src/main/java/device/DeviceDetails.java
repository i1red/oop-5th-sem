package device;

import java.util.Collections;
import java.util.List;

public class DeviceDetails {
    private final boolean isPeripheral;
    private final int energyConsumption;
    private final boolean hasCooler;
    private final DeviceFamily deviceFamily;
    private final List<Port> ports;

    public DeviceDetails(boolean isPeripheral, int energyConsumption, boolean hasCooler,
                         DeviceFamily deviceFamily, List<Port> ports) {
        this.isPeripheral = isPeripheral;
        this.energyConsumption = energyConsumption;
        this.hasCooler = hasCooler;
        this.deviceFamily = deviceFamily;
        this.ports = Collections.unmodifiableList(ports);
    }


    public boolean isPeripheral() {
        return isPeripheral;
    }

    public int getEnergyConsumption() {
        return energyConsumption;
    }

    public boolean hasCooler() {
        return hasCooler;
    }


    public DeviceFamily getDeviceFamily() {
        return deviceFamily;
    }

    public List<Port> getPorts() {
        return ports;
    }

}
