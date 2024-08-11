package com.orderinventoryapp.order_service.service;

import com.orderinventoryapp.inventory_service.models.Inventory;
import com.orderinventoryapp.order_service.models.Order;
import com.orderinventoryapp.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final RestTemplate restTemplate;

    @Autowired
    public OrderService(OrderRepository orderRepository, RestTemplate restTemplate) {
        this.orderRepository = orderRepository;
        this.restTemplate = restTemplate;
    }



    public Order createOrder(Long productId, int quantity) {
        // Check if the product exists in the inventory
        ResponseEntity<Inventory> response = restTemplate.getForEntity("http://localhost:8082/inventory/" + productId, Inventory.class);

        if (response.getStatusCode().is2xxSuccessful()) {
            Inventory inventory = response.getBody();

            if (inventory != null && inventory.getStock() >= quantity) {
                // Calculate new stock
                int newStock = inventory.getStock() - quantity;

                // Update the inventory stock
                Inventory updatedInventory = new Inventory();
                updatedInventory.setStock(newStock);

                try {
                    restTemplate.put("http://localhost:8082/inventory/" + productId, updatedInventory);
                } catch (RestClientException e) {
                    throw new RuntimeException("Failed to update inventory for product with ID " + productId, e);
                }

                // Create and save the order
                Order order = new Order();
                order.setProductId(productId);
                order.setQuantity(quantity);
                order.setOrderDate(LocalDateTime.now());
                return orderRepository.save(order);
            } else {
                throw new RuntimeException("Not enough stock for product with ID " + productId);
            }
        } else {
            throw new RuntimeException("Product not found in inventory");
        }
    }




    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

}
