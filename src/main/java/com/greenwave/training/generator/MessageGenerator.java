package com.greenwave.training.generator;

import com.typesafe.config.Config;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.BlockingQueue;

class MessageGenerator implements Runnable {

    private final Config config;
    private final BlockingQueue<MonitorMessage> messageQueue;
    private final List<Device> devices = new ArrayList<>();
    private final Random random = new Random(System.currentTimeMillis());

    public MessageGenerator(Config config, BlockingQueue<MonitorMessage> messageQueue) {
        this.config = config;
        this.messageQueue = messageQueue;

        DeviceType[] deviceTypes = DeviceType.values();

        int accountCount = config.getInt("account.count");
        int normalDeviceCount = config.getInt("device.count.normal");
        int suspiciousDeviceCount = config.getInt("device.count.suspicious");
        for (int accountId = 1; accountId <= accountCount; accountId++) {
            int deviceId = 1;
            for (int i = 0; i < normalDeviceCount; i++) {
                devices.add(new Device(accountId, deviceId++, deviceTypes[random.nextInt(deviceTypes.length)]));
            }
            for (int i = 0; i < suspiciousDeviceCount; i++) {
                devices.add(new Device(accountId, deviceId++, deviceTypes[random.nextInt(deviceTypes.length)], true));
            }
        }
    }

    @Override
    public void run() {
        Device device = devices.get(random.nextInt(devices.size()));
        List<DeviceCapability> capabilities = new ArrayList<>(device.getDeviceType().getCapabilities());
        DeviceCapability deviceCapability = capabilities.get(random.nextInt(capabilities.size()));

        int min = getValue(deviceCapability, "min");
        int max = getValue(deviceCapability, "max");
        int mean = getValue(deviceCapability, "mean");
        int deviation = getValue(deviceCapability, "deviation");

        int value;
        if (device.isSuspicious()) {
            // Linear distribution
            value = random.nextInt(max - min) + min;
        } else {
            value = (int) (random.nextGaussian() * deviation + mean);
            value = Math.min(Math.max(value, 0), max);
        }

        messageQueue.add(new MonitorMessage(device.getAccountId(), device.getDeviceId(),
                device.getDeviceType().name(), deviceCapability.name(),
                value, System.currentTimeMillis()));
    }

    private int getValue(DeviceCapability capability, String property) {
        return config.getInt(String.format("capability.%s.%s", capability.name(), property));
    }
}
