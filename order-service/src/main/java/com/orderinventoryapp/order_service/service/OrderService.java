package com.orderinventoryapp.order_service.service;

import com.orderinventoryapp.order_service.models.Order;
import com.orderinventoryapp.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order createOrder(Order order) {
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElse(null);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public String deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isPresent()) {
            Order order = orderOptional.get();
            orderRepository.delete(order);
            return "Order with id " + id + " has been deleted";
        } else {
            return "Order with id " + id + " not found";
        }
    }
}
