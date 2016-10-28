package com.greenwave.training.generator;

public class Device {

    private final int accountId;
    private final int deviceId;
    private final DeviceType deviceType;

    private final boolean suspicious;

    public Device(int accountId, int deviceId, DeviceType deviceType) {
        this(accountId, deviceId, deviceType, false);
    }

    public Device(int accountId, int deviceId, DeviceType deviceType, boolean suspicious) {
        this.accountId = accountId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.suspicious = suspicious;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public DeviceType getDeviceType() {
        return deviceType;
    }

    public boolean isSuspicious() {
        return suspicious;
    }
}
