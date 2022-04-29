package com.example.orderservice.service;


import com.example.orderservice.dto.OrderDTO;
import com.example.orderservice.entity.OrderEntity;
import com.example.orderservice.repository.OrderRepository;
import com.example.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService{

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;


    @Override
    public OrderDTO createOrder(OrderDTO orderDto) {
        orderDto.setOrderId(UUID.randomUUID().toString());
        orderDto.setTotalPrice(orderDto.getUnitPrice()*orderDto.getQty());
        OrderEntity entity = modelMapper.map(orderDto, OrderEntity.class);
        OrderEntity save = orderRepository.save(entity);
        return modelMapper.map(save,OrderDTO.class);
    }

    @Override
    public List<ResponseOrder> getOrdersByUserId(String userId) {
        Iterable<OrderEntity> orderEntities = orderRepository.findByUserId(userId);
        List<ResponseOrder> responseOrders = new ArrayList<>();
        orderEntities.forEach(orderEntity -> responseOrders.add(modelMapper.map(orderEntity,ResponseOrder.class)));
        return responseOrders;
    }

    @Override
    public OrderDTO getOrderByOrderId(String orderId) {
        OrderEntity orderEntity = orderRepository.findByOrderId(orderId);
        return modelMapper.map(orderEntity,OrderDTO.class);
    }
}
