package com.greenwave.training.generator;

import javax.ws.rs.client.Entity;
import javax.ws.rs.client.InvocationCallback;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.BlockingQueue;

class Reporter implements Runnable {
    private final BlockingQueue<MonitorMessage> messageQueue;
    private final WebTarget target;

    public Reporter(BlockingQueue<MonitorMessage> messageQueue, WebTarget target) {
        this.messageQueue = messageQueue;
        this.target = target;
    }

    @Override
    public void run() {
        try {
            while (true) {
                MonitorMessage message = messageQueue.take();
                target.request(MediaType.APPLICATION_JSON_TYPE)
                        .buildPost(Entity.entity(message, MediaType.APPLICATION_JSON_TYPE))
                        .submit(new InvocationCallback<Object>() {
                            @Override
                            public void completed(Object o) {}
                            @Override
                            public void failed(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
            }
        } catch (InterruptedException e) {
            // ignore and terminate
        }
    }
}
