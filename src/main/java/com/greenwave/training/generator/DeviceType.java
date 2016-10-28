package com.greenwave.training.generator;

import java.util.Set;
import static com.google.common.collect.ImmutableSet.*;

public enum DeviceType {

    TemperatureSensor(of(DeviceCapability.temperature)),
    VolumeSensor(of(DeviceCapability.volume)),
    PressureSensor(of(DeviceCapability.level)),
    MultiSensor(of(DeviceCapability.temperature, DeviceCapability.level));

    private final Set<DeviceCapability> capabilities;

    DeviceType(Set<DeviceCapability> capabilities) {
        this.capabilities = capabilities;
    }

    public Set<DeviceCapability> getCapabilities() {
        return capabilities;
    }

}
