package com.greenwave.training.generator;

public class MonitorMessage {

    private final int accountId;
    private final int deviceId;
    private final String deviceType;
    private final String capability;
    private final int value;
    private final long timestamp;

    public MonitorMessage(int accountId, int deviceId, String deviceType, String capability, int value, long timestamp) {
        this.accountId = accountId;
        this.deviceId = deviceId;
        this.deviceType = deviceType;
        this.capability = capability;
        this.value = value;
        this.timestamp = timestamp;
    }

    public int getAccountId() {
        return accountId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getCapability() {
        return capability;
    }

    public int getValue() {
        return value;
    }

    public long getTimestamp() {
        return timestamp;
    }
}
