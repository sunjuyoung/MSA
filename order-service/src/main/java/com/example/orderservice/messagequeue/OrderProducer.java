package com.example.orderservice.messagequeue;

import com.example.orderservice.dto.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderProducer {

    List<Field> fields = Arrays.asList( new Field("String",true,"order_id"),
            new Field("String",true,"user_id"),
            new Field("String",true,"product_id"),
            new Field("int32",true,"qty"),
            new Field("int32",true,"unit_price"),
            new Field("int32",true,"total_price"));

    Schema schema = Schema.builder()
            .type("struct")
            .fields(fields)
            .optional(false)
            .name("orders")
            .build();


    private final KafkaTemplate<String,String> kafkaTemplate;

    public OrderDTO send(String topic,OrderDTO orderDTO){
        Payload payload = Payload.builder()
                .order_id(orderDTO.getOrderId())
                .qty(orderDTO.getQty())
                .product_id(orderDTO.getProductId())
                .user_id(orderDTO.getUserId())
                .total_price(orderDTO.getTotalPrice())
                .unit_price(orderDTO.getUnitPrice())
                .build();

        KafkaOrderDTO kafkaOrderDTO = new KafkaOrderDTO(schema,payload);

        ObjectMapper mapper = new ObjectMapper();
        String jsonInString = "";
        try {
            jsonInString = mapper.writeValueAsString(kafkaOrderDTO);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        kafkaTemplate.send(topic,jsonInString);
        log.info("kafka OrderProducer  :" + kafkaOrderDTO);

        return  orderDTO;
    }



}
