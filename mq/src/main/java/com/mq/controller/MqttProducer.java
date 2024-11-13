package com.mq.controller;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class MqttProducer {
    private static final String QUEUE_NAME = "mqtt_queue";
    private static final String MQTT_TOPIC = "my/mqtt/topic";

    public static void main(String[] args) {
        try {
            ConnectionFactory factory = new ConnectionFactory();
            factory.setHost("localhost"); // RabbitMQ 服务器地址

            try (Connection connection = factory.newConnection();
                 Channel channel = connection.createChannel()) {

                channel.queueDeclare(QUEUE_NAME, false, false, false, null);

                String message = "Hello, MQTT via RabbitMQ!";
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());

                System.out.println("Message sent via RabbitMQ: " + message);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
