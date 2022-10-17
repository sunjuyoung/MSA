package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.messagequeue.KafkaProducer;
import com.example.orderservice.messagequeue.OrderProducer;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;
    private final KafkaProducer kafkaProducer;
    private final OrderProducer orderProducer;

   @PostMapping("/{userId}/order")
    public ResponseEntity<?> createOrder(@PathVariable("userId")String userId,@RequestBody RequestOrder requestOrder){
       OrderDTO orderDto = modelMapper.map(requestOrder, OrderDTO.class);
       orderDto.setUserId(userId);

       //OrderDTO order = orderService.createOrder(orderDto);

       /*  kafka */
       orderDto.setOrderId(UUID.randomUUID().toString());
       orderDto.setTotalPrice(requestOrder.getUnitPrice()*requestOrder.getQty());

       //kafka , send order
       kafkaProducer.send("example-catalog-topic",orderDto);
       orderProducer.send("orders",orderDto);


       ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);
       return ResponseEntity.ok().body(responseOrder);
   }



    @GetMapping("/{userId}/order")
    public ResponseEntity<List<ResponseOrder>> getOrderByUserId(@PathVariable("userId")String userId){
        List<ResponseOrder> ordersByUserId = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(ordersByUserId);
    }


}
