package com.greenwave.training.generator;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

import javax.ws.rs.client.*;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {
        Config config = ConfigFactory.load();
        BlockingQueue<MonitorMessage> messageQueue = new ArrayBlockingQueue<>(100);
        startReporters(config, messageQueue);
        startGenerator(config, messageQueue);
    }

    private static void startGenerator(Config config, BlockingQueue<MonitorMessage> messageQueue) {
        int reporterRate = 1000 / config.getInt("reporter.rate");
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleAtFixedRate(new MessageGenerator(config, messageQueue), 0, reporterRate, TimeUnit.MILLISECONDS);
    }

    private static void startReporters(Config config, BlockingQueue<MonitorMessage> messageQueue) {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(config.getString("reporter.endpoint"));
        int reporterThreads = config.getInt("reporter.threads");
        ExecutorService reporterExecutorService = Executors.newFixedThreadPool(reporterThreads);
        for (int i=0; i < reporterThreads; i++) {
            reporterExecutorService.submit(new Reporter(messageQueue, target));
        }
    }


}
