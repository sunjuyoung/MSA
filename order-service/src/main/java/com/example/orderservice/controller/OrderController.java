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
       log.info("before add orders-service");
       OrderDTO orderDto = modelMapper.map(requestOrder, OrderDTO.class);
       orderDto.setUserId(userId);

       OrderDTO order = orderService.createOrder(orderDto);

       /*  kafka */
//       orderDto.setOrderId(UUID.randomUUID().toString());
//       orderDto.setTotalPrice(requestOrder.getUnitPrice()*requestOrder.getQty());
//
//       //kafka , send order
//       kafkaProducer.send("example-catalog-topic",orderDto);
//       orderProducer.send("orders3",orderDto);


       ResponseOrder responseOrder = modelMapper.map(orderDto, ResponseOrder.class);
       log.info("after add orders-service");
       return ResponseEntity.ok().body(responseOrder);
   }

    @GetMapping("/{userId}/order")
    public ResponseEntity<List<ResponseOrder>> getOrderByUserId(@PathVariable("userId")String userId) throws Exception{
        log.info("before response orders-service");
        List<ResponseOrder> ordersByUserId = orderService.getOrdersByUserId(userId);

        try{
            Thread.sleep(1000);
            throw new Exception("장애 발생");
        }catch (InterruptedException e){
            log.error(e.getMessage());

        }

        log.info("after response orders-service");
        return ResponseEntity.ok().body(ordersByUserId);
    }


}
