package com.orderinventoryapp.order_service.repository;

import com.orderinventoryapp.order_service.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>{
    Optional<Order> findByProductName(String productName);

}
