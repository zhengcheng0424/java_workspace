package com.mq.service;

import com.mq.dao.Temperature;
import com.mq.mapper.TemperatureMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import com.alibaba.fastjson.JSONObject;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
@Service
@Transactional
public class MqttConvertService {
    final TemperatureMapper temperatureMapper;
    final RabbitTemplate rabbitTemplate;
    
    public void sendProducerMessage(String mqMsg) {
        Temperature temperature = JSONObject.parseObject(mqMsg, Temperature.class);
        assembleTemperatureDao(temperature);
        try {
            String jsonString = JSONObject.toJSONString(temperature);
            rabbitTemplate.convertAndSend("demo01", jsonString);
            System.out.println("temperature send to demo01: " + jsonString);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
    }

    public void saveMqttMessages(String topic, String mqMsg) {

        Temperature temperature = JSONObject.parseObject(mqMsg, Temperature.class);
        assembleTemperatureDao(temperature);
        try {
            temperatureMapper.insert(temperature);
        } catch (Exception e) {
            System.out.println("Exception: " + e);
        }
        System.out.println("4Message arrived: " + topic + " : " + mqMsg);
    }

    private static void assembleTemperatureDao(Temperature temperature) {

        List<BigDecimal> coordinates = Arrays.stream(temperature.getAddress().split(","))
                .filter(Objects::nonNull).map(BigDecimal::new).toList();
        temperature.setLongitude(coordinates.get(0));
        temperature.setLatitude(coordinates.get(1));
        temperature.setCreateTime(LocalDateTime.now());
        temperature.setUpdateTime(LocalDateTime.now());
    }

}
