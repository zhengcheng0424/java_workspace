package com.mq.component;

import com.mq.controller.Mq;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class MqttInitializer {
    final Mq mq;

    @PostConstruct
    public void init() {
        try {
            mq.mqReceiver();
        } catch (MqttException e) {
            throw new RuntimeException(e);
        }
    }
}
