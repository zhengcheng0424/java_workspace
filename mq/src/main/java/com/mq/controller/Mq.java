package com.mq.controller;

import com.mq.service.MqttConvertService;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.stereotype.Component;
@RequiredArgsConstructor
@Component
public class Mq {
    final MqttConvertService mqttConvertService;
    private final static String mqServerIp = "47.122.31.247";
    private final static Integer mqServerPort = 1883;
    private final static String username = "ceegesis";
    private final static String pwd = "asaHJ123NDALq1231ddxKJF131";
    private final static String subscribePub = "/temperature/#";

    public void mqReceiver() throws MqttException {
        String serverUri = "tcp://" + mqServerIp + ":" + mqServerPort;
        MqttClient client = new MqttClient(serverUri, MqttClient.generateClientId());

        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(true);
        // 如果需要用户名和密码
        options.setUserName(username);
        options.setPassword(pwd.toCharArray());

        client.connect(options);

        client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                System.out.println("Connection lost!");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) {
                String mqMsg = new String(message.getPayload());
                // mqttConvertService.saveMqttMessages(topic, mqMsg);
                mqttConvertService.sendProducerMessage(mqMsg);
            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {
                // 不需要在订阅者中实现
            }
        });

        client.subscribe(subscribePub, 1); // QoS
    }
}
