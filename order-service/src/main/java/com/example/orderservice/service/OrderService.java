package com.example.orderservice.service;

import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.vo.ResponseOrder;

import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDto);
    List<ResponseOrder> getOrdersByUserId(String userId);
    OrderDTO getOrderByOrderId(String orderId);

}
