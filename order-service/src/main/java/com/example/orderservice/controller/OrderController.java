package com.example.orderservice.controller;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.service.OrderService;
import com.example.orderservice.vo.RequestOrder;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/order-service")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final ModelMapper modelMapper;

   @PostMapping("/{userId}/order")
    public ResponseEntity<?> createOrder(@PathVariable("userId")String userId,@RequestBody RequestOrder requestOrder){
       OrderDTO orderDTO = modelMapper.map(requestOrder, OrderDTO.class);
       orderDTO.setUserId(userId);
       OrderDTO order = orderService.createOrder(orderDTO);
       return ResponseEntity.ok().body(modelMapper.map(order, ResponseOrder.class));
   }

    @GetMapping("/{userId}/order")
    public ResponseEntity<?> getOrderByUserId(@PathVariable("userId")String userId){
        List<ResponseOrder> ordersByUserId = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok().body(ordersByUserId);
    }


}
