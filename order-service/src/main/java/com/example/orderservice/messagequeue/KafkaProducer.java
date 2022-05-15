package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.OrderDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String,String> kafkaTemplate;

    public OrderDTO send(String topic,OrderDTO orderDTO){
        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(orderDTO);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        kafkaTemplate.send(topic,jsonInString);
        log.info("kafka producer send order :" + orderDTO);

        return  orderDTO;
    }



}
