import java.util.Collections;
import java.util.List;

public class DeviceDetails {
    private final boolean isPeripheral;
    private final int energyConsumption;
    private final boolean hasCooler;
    private final List<Port> ports;

    public DeviceDetails(boolean isPeripheral, int energyConsumption, boolean hasCooler, List<Port> ports) {
        this.isPeripheral = isPeripheral;
        this.energyConsumption = energyConsumption;
        this.hasCooler = hasCooler;
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

    public List<Port> getPorts() {
        return ports;
    }
}
